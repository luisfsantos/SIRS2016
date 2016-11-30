from django.shortcuts import render

from django.shortcuts import render
from django.http import Http404, HttpResponseRedirect
from rest_framework.views import APIView
from rest_framework.decorators import api_view, throttle_classes
from rest_framework.response import Response
from rest_framework import status
from rest_framework.throttling import AnonRateThrottle
from django.contrib.auth.models import User
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required, user_passes_test
from django.utils.decorators import method_decorator
from Common.responses import createResponse
from Users.forms import UserForm, UserProfileForm, LoginForm
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

@login_required
def register(request):
    """
    List all users, or create a new user.
    """
    if request.user.is_superuser:
        if request.method == "POST":
            uform = UserForm(data=request.POST)
            pform = UserProfileForm(data=request.POST)
            if uform.is_valid() and pform.is_valid():
                user = uform.save()
                pform = UserProfileForm(data=request.POST, instance=user.userprofile)
                pform.save()
                return HttpResponseRedirect('/menu/ingredients')
        else:
            uform = UserForm()
            pform = UserProfileForm()

        return render(request, 'register.html', {'uform': uform, 'pform': pform})
    return render(request, 'register.html')

def login_user(request):
    form = LoginForm()
    if request.method == 'POST':
        username = request.data.get("username", '')
        password = request.data.get("password", '')

        user = authenticate(username=username, password=password)
        if user:
            if user.is_active:
                login(request, user)
                return HttpResponseRedirect('/accounts')
            else:
                return render(request, 'login.html', {'form': form, 'active': False, 'invalid': False})
        else:
            return render(request, 'login.html', {'form': form, 'active': False, 'invalid': True})
    return render(request, 'login.html', {'form': form, 'active': True, 'invalid': False})