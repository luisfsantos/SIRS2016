from django.shortcuts import render

# Create your views here.
from django.shortcuts import render
from django.http import Http404, HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required, user_passes_test
from Tables.models import TableModel
# Create your views here.

@login_required
def posView(request):
    """
    Register Staff to the System
    """
    if request.user.is_superuser:
        if request.method == "POST":
            return HttpResponseRedirect('/menu/ingredients')

    return render(request, 'pos.html', {'table1': TableModel.objects.get(number=1), 'table2': TableModel.objects.get(number=2), 'table3': TableModel.objects.get(number=3), 'table4': TableModel.objects.get(number=4)})

def login_user(request):
    form = 1