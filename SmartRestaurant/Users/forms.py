from django import forms
from Users.models import UserProfile
from django.contrib.auth.models import User
from django.forms import ModelForm

class UserForm(ModelForm):
    class Meta:
        model = User
        fields = ['email', 'username', 'password', 'first_name', 'last_name']

class UserProfileForm(ModelForm):
    class Meta:
        model = UserProfile
        fields = ['nif',]

