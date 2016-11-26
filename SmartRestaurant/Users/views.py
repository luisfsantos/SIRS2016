from django.shortcuts import render

from Users.serializers import UserSerializer
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.decorators import api_view, throttle_classes
from rest_framework.response import Response
from rest_framework import status
from rest_framework.throttling import AnonRateThrottle
from django.contrib.auth.models import User
from django.contrib.auth import authenticate, login
from django.contrib.auth.decorators import login_required
from Common.responses import createResponse
# Create your views here.

class DailyRegisterThrottle(AnonRateThrottle):
    scope = "sustained"
    rate = "5/day"

class BurstLoginThrottle(AnonRateThrottle):
    scope = "burst"
    rate = "5/s"

class DailyLoginThrottle(AnonRateThrottle):
    scope = "sustained"
    rate = "100/day"

class AccountList(APIView):
    """
    List all users, or create a new user.
    """
    def get(self, request, format=None):
        users = User.objects.all()
        serializer = UserSerializer(users, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        user = UserSerializer(data=request.data)
        if user.is_valid():
            user.save()
            return Response(user.data, status=status.HTTP_201_CREATED)
        return Response(user.errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'POST'])
#@throttle_classes([DailyRegisterThrottle])
def register(request):
    if request.method == 'POST':
        if len(str(request.data.get('nif', 0))) != 9:
            return Response(createResponse("nif","The NIF must have 9 digits"), status=status.HTTP_400_BAD_REQUEST)
        user = UserSerializer(data=request.data)
        if user.is_valid():
            user.save()
            return Response(user.data, status=status.HTTP_201_CREATED)
        return Response(user.errors, status=status.HTTP_400_BAD_REQUEST)
    return Response(createResponse("Registration", "Welcome to the Registration Page"))


@api_view(['GET', 'POST'])
#@throttle_classes([DailyLoginThrottle, BurstLoginThrottle])
def login_user(request):
    if request.method == 'POST':
        username = request.data.get("username", '')
        password = request.data.get("password", '')

        user = authenticate(username=username, password=password)
        if user:
            if user.is_active:
                login(request, user)
                return Response(createResponse("Login", "Success!"), status=status.HTTP_200_OK)
            else:
                return Response(createResponse("Login", "Your SmartRestaurant account is disabled."))
        else:
            return Response(createResponse("Login", "Invalid login details supplied."))
    return Response(createResponse("Login", "Please, have a seat and login ;-)"))

@api_view(['GET', 'POST'])
#@throttle_classes([DailyLoginThrottle, BurstLoginThrottle])
def logout_user(request):
    if request.method == 'POST':
        username = request.data.get("username", '')
        password = request.data.get("password", '')

        user = authenticate(username=username, password=password)
        if user:
            if user.is_active:
                login(request, user)
                return Response("Login Successful", status=status.HTTP_200_OK)
            else:
                return Response("Your SmartRestaurant account is disabled.")
        else:
            return Response("Invalid login details supplied.")
    return Response("Please, have a seat and login ;-)")


@api_view(['GET'])
#@throttle_classes([DailyLoginThrottle, BurstLoginThrottle])
@login_required()
def test_loggedin(request):
    return Response(createResponse("Test", "You are logged in."))


class AccountDetail(APIView):
    """
    Retrieve a user instance.
    """
    def get_object(self, pk):
        try:
            return User.objects.get(pk=pk)
        except User.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        user = self.get_object(pk)
        serializer = UserSerializer(user)
        return Response(serializer.data)
