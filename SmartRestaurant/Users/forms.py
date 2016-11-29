from django import forms
from Users.models import UserProfile
from django.contrib.auth.models import User

class UserForm(forms.Form):
    class Meta:
        model = User
        fields = ['email', 'username', 'password', 'first_name', 'last_name']

class UserProfileForm(forms.Form):
    class Meta:
        model = UserProfile
        fields = ['nif',]

