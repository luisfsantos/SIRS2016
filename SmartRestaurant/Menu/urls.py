from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Menu import APIviews

urlpatterns = [
    url(r'^mains/$', APIviews.MainListAPI.as_view()),
    url(r'^appetizers/$', APIviews.AppetizerListAPI.as_view()),
    url(r'^desserts/$', APIviews.DessertListAPI.as_view()),
    url(r'^ingredients/$', APIviews.IngredientListAPI.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)