from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Users import views
from Users import APIviews

urlpatterns = [
    url(r'^accounts/$', APIviews.AccountListAPI),
    url(r'^register/$', views.register),
    url(r'^login/$', APIviews.login_userAPI),
    url(r'^logout/$', APIviews.logout_userAPI),
    url(r'^test/$', APIviews.test_loggedinAPI),
]

urlpatterns = format_suffix_patterns(urlpatterns)