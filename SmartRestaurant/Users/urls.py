from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Users import views

urlpatterns = [
    url(r'^accounts/$', views.AccountList.as_view()),
    url(r'^accounts/(?P<pk>[0-9]+)/$', views.AccountDetail.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)