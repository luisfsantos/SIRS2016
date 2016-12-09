from django.shortcuts import render

from Users.serializers import UserSerializer
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.decorators import api_view, throttle_classes
from rest_framework.response import Response
from rest_framework import status
from rest_framework.throttling import AnonRateThrottle
from django.contrib.auth.models import User, Group
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from Common.responses import createResponse
from Common.throttle import DailyRegisterThrottle, DailyLoginThrottle, BurstLoginThrottle, BurstRegisterThrottle, DailyAPIThrottle
# Create your views here.



@api_view(['GET', 'POST'])
@throttle_classes([DailyRegisterThrottle, BurstRegisterThrottle])
def registerAPI(request):
    if request.method == 'POST':
        # nif validation
        if len(str(request.data.get('nif', 0))) != 9:
            return Response(createResponse("nif","The NIF must have 9 digits"), status=status.HTTP_400_BAD_REQUEST)
        #non duplicate emails
        if User.objects.filter(email=request.data.get("email", '')).exists():
            return Response(createResponse("email", "This email has already been used."), status=status.HTTP_400_BAD_REQUEST)
        user_serializer = UserSerializer(data=request.data)
        if user_serializer.is_valid():
            user_serializer.save()
            user = User.objects.get(username=request.data.get("username", ''))
            g = Group.objects.get(name='users')
            g.user_set.add(user)
            login(request, user)
            return Response(user_serializer.data, status=status.HTTP_201_CREATED)
        return Response(user_serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    return Response(createResponse("Registration", "Welcome to the Registration Page"))


@api_view(['GET', 'POST'])
@throttle_classes([DailyLoginThrottle, BurstLoginThrottle])
def login_userAPI(request):
    if request.method == 'POST':
        username = request.data.get("username", '')
        password = request.data.get("password", '')

        user = authenticate(username=username, password=password)
        if user:
            if user.is_active:
                login(request, user)
                serialize = UserSerializer(user)
                return Response(createResponse("Login", serialize.data), status=status.HTTP_200_OK)
            else:
                return Response(createResponse("Login", "Your SmartRestaurant account is disabled."), status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response(createResponse("Login", "Invalid login credentials."), status=status.HTTP_400_BAD_REQUEST)
    return Response(createResponse("Login", "Please, have a seat and login ;-)"))

@api_view(['GET'])
def logout_userAPI(request):
    if request.user.is_authenticated():
        username = request.user.username
        request.user.table_set.clear()
        logout(request)
        return Response(createResponse(username, "Logged out!"))
    return Response(createResponse("Logout", "This logs you out!"))


@api_view(['GET'])
@throttle_classes([DailyAPIThrottle])
@login_required()
def test_loggedinAPI(request):
    return Response(createResponse("Test", "You are logged in."))

