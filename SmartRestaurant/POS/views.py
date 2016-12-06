from django.contrib import messages
from django.shortcuts import render, redirect

# Create your views here.
from django.shortcuts import render
from django.http import Http404, HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required, user_passes_test
from Tables.models import TableModel
# Create your views here.


def posView(request):
    if request.user.is_anonymous:
        messages.add_message(request, messages.INFO, "You can't access the POS console if you aren't logged in.")
        return redirect('/login')
    else:
        if request.user.groups.filter(name='staff') or request.user.is_superuser:
            if request.method == "POST":
                return HttpResponseRedirect('/menu/ingredients')
            else:
                return render(request, 'pos.html', {'user_name': request.user.first_name + request.user.last_name,
                                                    'table1': TableModel.objects.get(number=1),
                                                    'table2': TableModel.objects.get(number=2),
                                                    'table3': TableModel.objects.get(number=3),
                                                    'table4': TableModel.objects.get(number=4)})
        else:
            messages.add_message(request, messages.INFO, "You do not have permission to view the POS console.")
            return redirect('/login')


def login_user(request):
    form = 1