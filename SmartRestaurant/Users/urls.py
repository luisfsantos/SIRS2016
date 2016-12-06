from django.conf.urls import url
from django.views.generic import RedirectView
from rest_framework.urlpatterns import format_suffix_patterns
from Users import views
from Users import APIviews

app_name='accounts'
urlpatterns = [
    url(r'^accounts/$', APIviews.AccountListAPI),
    url(r'^register/$', views.register_staff),
    url(r'^login/$', views.login_staff, name = 'login'),
    url(r'^/$', RedirectView.as_view(pattern_='login')),
    url(r'^logout/$', views.logout_user, name='logout'),
    url(r'^test/$', APIviews.test_loggedinAPI),
]

urlpatterns = format_suffix_patterns(urlpatterns)