from django.shortcuts import render
from django.http import Http404, HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required, user_passes_test
from Users.forms import UserForm, UserProfileForm, LoginForm
# Create your views here.

@login_required
def register(request):
    """
    Register Staff to the System
    """
    if request.user.is_superuser:
        if request.method == "POST":
            uform = UserForm(data=request.POST)
            pform = UserProfileForm(data=request.POST)
            if uform.is_valid() and pform.is_valid():
                user = uform.save()
                pform = UserProfileForm(data=request.POST, instance=user.userprofile)
                pform.save()
                return HttpResponseRedirect('/accounts')
        else:
            uform = UserForm()
            pform = UserProfileForm()

        return render(request, 'register.html', {'uform': uform, 'pform': pform})
    return render(request, 'register.html')

def login_user(request):
    form = LoginForm()
    if request.method == 'POST':
        form = LoginForm(data=request.POST)
        if form.is_valid():
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            raise Exception
            user = authenticate(username=username, password=password)
            if user:
                if user.is_active:
                    login(request, user)
                    return HttpResponseRedirect('/accounts')
                else:
                    return render(request, 'login.html', {'form': form, 'active': False, 'invalid': False})
            else:
                return render(request, 'login.html', {'form': form, 'active': True, 'invalid': True})
    return render(request, 'login.html', {'form': form, 'active': True, 'invalid': False})