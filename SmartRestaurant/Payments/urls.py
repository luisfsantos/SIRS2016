from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Payments import APIviews

urlpatterns = [
    url(r'^paypal/$', APIviews.paypal_api),
    url(r'^cash/$', APIviews.paypal_api),
]

urlpatterns = format_suffix_patterns(urlpatterns)