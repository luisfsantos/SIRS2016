from django.contrib import messages
from django.shortcuts import render, redirect

# Create your views here.
from django.shortcuts import render
from django.http import Http404, HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required, user_passes_test

from Orders.models import UserOrders, Order
from POS.forms import OrderIDForm
from Tables.models import TableModel
# Python logging package
import logging

# Standard instance of a logger with __name__
stdlogger = logging.getLogger(__name__)
# Create your views here.


def posView(request):
    if request.user.is_anonymous:
        messages.add_message(request, messages.INFO, "You can't access the POS console if you aren't logged in.")
        return redirect('/login')
    else:
        if request.user.groups.filter(name='staff') or request.user.is_superuser:
            tables = TableModel.objects.all().order_by('number')
            return render(request, 'POS/pos.html', {'staff_name': request.user.first_name + " " + request.user.last_name,
                                                    'tables': tables})
        else:
            messages.add_message(request, messages.INFO, "You do not have permission to view the POS console.")
            whois_name = str(
                request.user.get_full_name()) if request.user.get_full_name() is not None else request.user.username
            whois_id = str(
                request.user.userprofile.nif) if request.user.userprofile.nif is not None else request.user.email
            stdlogger.warning("The user: " + whois_name + " " + whois_id + " attempted to use the POS console.")
            return redirect('/login')


def view_user_info(request):
    if request.user.is_anonymous:
        messages.add_message(request, messages.INFO, "You can't access the POS console if you aren't logged in.")
        return redirect('/login')
    else:
        if request.user.is_superuser:
            if request.method == "POST":
                form = OrderIDForm(data=request.POST)
                if form.is_valid():
                    try:
                        user_order = UserOrders.objects.get(order=form.cleaned_data.get('identifier'))
                        order = Order.objects.get(identifier=form.cleaned_data.get('identifier'))
                        return render(request, 'POS/clientinfo.html',
                               {'staff_name': request.user.first_name + " " + request.user.last_name,
                                'user_order': user_order,
                                'order': order})
                    except UserOrders.DoesNotExist:
                        redirect('/pos/view')
            else:
                return redirect('/pos/view')
        else:
            messages.add_message(request, messages.INFO, "You do not have permission to view data.")
            whois_name = str(
                request.user.get_full_name()) if request.user.get_full_name() is not None else request.user.username
            whois_id = str(
                request.user.userprofile.nif) if request.user.userprofile.nif is not None else request.user.email
            stdlogger.warning("The user: " + whois_name + " " + whois_id + " attempted to view user data.")
            return redirect('/login')