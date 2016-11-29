from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Users import APIviews

urlpatterns = [
    url(r'^accounts/$', APIviews.AccountList),
    url(r'^register/$', APIviews.register),
    url(r'^login/$', APIviews.login_user),
    url(r'^logout/$', APIviews.logout_user),
    url(r'^test/$', APIviews.test_loggedin),
]

urlpatterns = format_suffix_patterns(urlpatterns)