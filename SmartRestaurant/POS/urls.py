from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from POS import views

urlpatterns = [
    url(r'^view/$', views.posView),
]

urlpatterns = format_suffix_patterns(urlpatterns)