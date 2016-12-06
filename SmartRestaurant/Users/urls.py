from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Users import views
from Users import APIviews

urlpatterns = [
    url(r'^accounts/$', APIviews.AccountListAPI),
    url(r'^register/$', views.register_staff),
    url(r'^login/$', views.login_staff),
    url(r'^logout/$', views.logout_user),
    url(r'^test/$', APIviews.test_loggedinAPI),
]

urlpatterns = format_suffix_patterns(urlpatterns)