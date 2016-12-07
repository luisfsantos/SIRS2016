from django import forms


class CleanTableForm(forms.Form):
    table_id = forms.CharField(label="table_id", max_length=100,
                               widget=forms.TextInput(attrs={'name': 'table_id'}))
