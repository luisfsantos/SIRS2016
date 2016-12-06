from django.shortcuts import render, redirect
from django.http import Http404
from Tables.models import TableModel
from Tables.forms import CleanTableForm
# Create your views here.
def clean_table(request):
    """
    Register Staff to the System
    """
    if request.user.groups.filter(name='staff') or request.user.is_superuser:
        if request.method == "POST":
            ctform = CleanTableForm(data=request.POST)
            if ctform.is_valid():
                try:
                    table = TableModel.objects.get(ctform.cleaned_data.get('table_id'))
                    table.user = None
                    table.order_set.all().clean()
                except TableModel.DoesNotExist:
                    redirect('/pos/view')

        return redirect('/pos/view')
    else:
        raise Http404