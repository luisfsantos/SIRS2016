from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Menu import views

urlpatterns = [
    url(r'^mains/$', views.MainList.as_view()),
    url(r'^appetizers/$', views.AppetizerList.as_view()),
    url(r'^desserts/$', views.DessertList.as_view()),
    url(r'^ingredients/$', views.IngredientList.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)