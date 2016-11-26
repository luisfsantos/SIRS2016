from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Users import views

urlpatterns = [
    url(r'^accounts/$', views.AccountList),
    url(r'^register/$', views.register),
    url(r'^login/$', views.login_user),
    url(r'^logout/$', views.logout_user),
    url(r'^test/$', views.test_loggedin),
]

urlpatterns = format_suffix_patterns(urlpatterns)