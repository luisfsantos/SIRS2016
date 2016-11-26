from django.shortcuts import render

from Users.serializers import UserSerializer
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.decorators import api_view, throttle_classes
from rest_framework.response import Response
from rest_framework import status
from rest_framework.throttling import AnonRateThrottle
from django.contrib.auth.models import User
from django.contrib.auth import authenticate, login, logout
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

@api_view(['GET'])
@login_required()
def AccountList(request):
    """
    List all users, or create a new user.
    """
    users = User.objects.all()
    serializer = UserSerializer(users, many=True)
    return Response(serializer.data)



@api_view(['GET', 'POST'])
#@throttle_classes([DailyRegisterThrottle])
def register(request):
    if request.method == 'POST':
        if len(str(request.data.get('nif', 0))) != 9:
            return Response(createResponse("nif","The NIF must have 9 digits"), status=status.HTTP_400_BAD_REQUEST)
        user_serializer = UserSerializer(data=request.data)
        if user_serializer.is_valid():
            user_serializer.save()
            user = User.objects.get(username=request.data.get("username", ''))
            login(request, user)
            return Response(user_serializer.data, status=status.HTTP_201_CREATED)
        return Response(user_serializer.errors, status=status.HTTP_400_BAD_REQUEST)
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
                return Response(createResponse("Login", "Your SmartRestaurant account is disabled."), status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response(createResponse("Login", "Invalid login details supplied."), status=status.HTTP_400_BAD_REQUEST)
    return Response(createResponse("Login", "Please, have a seat and login ;-)"))

@api_view(['GET'])
#@throttle_classes([DailyLoginThrottle, BurstLoginThrottle])
def logout_user(request):
    if request.user.is_authenticated():
        username = request.user.username
        logout(request)
        return Response(createResponse(username, "Logged out!"))
    return Response(createResponse("Logout", "This logs you out!"))


@api_view(['GET'])
#@throttle_classes([DailyLoginThrottle, BurstLoginThrottle])
@login_required()
def test_loggedin(request):
    return Response(createResponse("Test", "You are logged in."))

