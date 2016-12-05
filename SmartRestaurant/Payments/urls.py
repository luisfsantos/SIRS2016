from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Payments import APIviews

urlpatterns = [
    url(r'^paypal/$', APIviews.paypalAPI),
    url(r'^cash/$', APIviews.paypalAPI),
]

urlpatterns = format_suffix_patterns(urlpatterns)