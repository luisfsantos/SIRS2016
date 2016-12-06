from django import forms
from Users.models import UserProfile
from django.contrib.auth.models import User
from django.forms import ModelForm
from django.contrib.auth import password_validation
from django.utils.translation import ugettext, ugettext_lazy as _


class CleanTableForm(forms.Form):
    table_id = forms.CharField(label="table_id", max_length=100,
                               widget=forms.TextInput(attrs={'name': 'table_id'}))
