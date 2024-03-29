from django.contrib import messages
from django.contrib.auth.models import Group
from django.shortcuts import render, redirect
from django.http import Http404, HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from Users.forms import UserForm, UserProfileForm, LoginForm
from rest_framework.response import Response
from django.contrib.auth.models import User
# Create your views here.
from Users.serializers import UserSerializer


def register_staff(request):
    """
    Register Staff to the System
    """
    if request.user.is_superuser:
        if request.method == "POST":
            uform = UserForm(data=request.POST)
            pform = UserProfileForm(data=request.POST)
            if uform.is_valid() and pform.is_valid():
                user = uform.save()
                g = Group.objects.get(name='staff')
                g.user_set.add(user)
                pform = UserProfileForm(data=request.POST, instance=user.userprofile)
                pform.save()
                return HttpResponseRedirect('/admin')
        else:
            uform = UserForm()
            pform = UserProfileForm()

        return render(request, 'register.html', {'uform': uform, 'pform': pform})
    else:
        messages.add_message(request, messages.INFO, "You can't register users if you don't have Admin status.")
        return redirect('/login')

def login_staff(request):
    form = LoginForm()
    if request.method == 'POST':
        form = LoginForm(data=request.POST)
        if form.is_valid():
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            user = authenticate(username=username, password=password)
            if user:
                if user.is_active:
                    login(request, user)
                    return HttpResponseRedirect('/pos/view')
                else:
                    return render(request, 'login.html', {'form': form, 'active': False, 'invalid': False})
            else:
                return render(request, 'login.html', {'form': form, 'active': True, 'invalid': True})
    return render(request, 'login.html', {'form': form, 'active': True, 'invalid': False})

@login_required()
def AccountList(request):
    """
    List all users, or create a new user.
    """
    if request.user.is_superuser:
        users = User.objects.all()
        serializer = UserSerializer(users, many=True)
        return Response(serializer.data)
    else:
        raise Http404


def logout_user(request):
    logout(request)
    messages.add_message(request, messages.INFO, "You are now logged out.")
    return redirect('/login')