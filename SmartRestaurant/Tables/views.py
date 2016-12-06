from django.shortcuts import render, redirect
from django.http import Http404
from Tables.models import TableModel
from Tables.forms import CleanTableForm

# Python logging package
import logging
import uuid

# Standard instance of a logger with __name__
stdlogger = logging.getLogger(__name__)


# Create your views here.
def clean_table(request):
    """
    Register Staff to the System
    """
    if not request.user.is_anonymous:
        if request.user.groups.filter(name='staff') or request.user.is_superuser:
            if request.method == "POST":
                ctform = CleanTableForm(data=request.POST)
                if ctform.is_valid():
                    try:
                        table = TableModel.objects.get(table_id=ctform.cleaned_data.get('table_id'))
                        id = uuid.uuid4() + ": "
                        stdlogger.warning(id + "The table: " + ctform.cleaned_data.get('table_id') + " is getting cleaned by "
                                          + request.user.get_full_name() + " " + request.user.userprofile.nif)
                        table.user = None

                        stdlogger.warning(id + "This affects:")
                        for order in table.order_set.all():
                            stdlogger.warning(id + "Order: " + order.identifier + " which has status " + order.get_status_display())
                            order.status = 'AR'
                            order.save()
                        table.save()
                    except TableModel.DoesNotExist:
                        redirect('/pos/view')

            return redirect('/pos/view')
        else:
            raise Http404
    else:
        raise Http404
