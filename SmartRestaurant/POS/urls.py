from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from POS import views

app_name='POS'

urlpatterns = [
    url(r'^view/$', views.posView, name='view'),
    url(r'^view/order/user/$', views.view_user_info),
    url(r'^view/order/$', views.view_order_info),
    url(r'^order/edit/processed/$', views.process_order),
    url(r'^order/pay/cash/$', views.receive_cash_order),
]

urlpatterns = format_suffix_patterns(urlpatterns)