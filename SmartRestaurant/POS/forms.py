from django import forms



class OrderIDForm(forms.Form):
    identifier = forms.UUIDField(label="identifier",
                               widget=forms.TextInput(attrs={'name': 'identifier'}))