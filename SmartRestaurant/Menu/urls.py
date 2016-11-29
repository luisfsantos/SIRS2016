from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Menu import APIviews

urlpatterns = [
    url(r'^mains/$', APIviews.MainList.as_view()),
    url(r'^appetizers/$', APIviews.AppetizerList.as_view()),
    url(r'^desserts/$', APIviews.DessertList.as_view()),
    url(r'^ingredients/$', APIviews.IngredientList.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)