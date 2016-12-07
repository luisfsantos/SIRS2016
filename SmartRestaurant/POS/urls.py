from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from POS import views

app_name='POS'

urlpatterns = [
    url(r'^view/$', views.posView, name='view'),
    url(r'^view/order/$', views.view_user_info),
]

urlpatterns = format_suffix_patterns(urlpatterns)