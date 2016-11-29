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
from django.contrib.auth.decorators import login_required
from Common.responses import createResponse
from Users.forms import UserForm, UserProfileForm
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

def register(request):
    """
    List all users, or create a new user.
    """
    if request.method == "POST":
        uform = UserForm(data=request.POST)
        pform = UserProfileForm(data=request.POST)
        if uform.is_valid() and pform.is_valid():
            user = uform.save()
            profile = pform.save(commit=False)
            profile.user = user
            profile.save()
            return HttpResponseRedirect('/menu/ingredients')
    else:
        uform = UserForm()
        pform = UserProfileForm()
        return render(request, 'register.html', {'uform': uform, 'pform': pform})