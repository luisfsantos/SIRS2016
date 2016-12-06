from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Tables import views

urlpatterns = [
    url(r'^clean/$', views.clean_table),
]

urlpatterns = format_suffix_patterns(urlpatterns)