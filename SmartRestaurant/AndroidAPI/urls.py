from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Menu import APIviews as menu_views
from Users import APIviews as user_views

urlpatterns = [
    url(r'^menu/mains/$', menu_views.MainList.as_view()),
    url(r'^menu/appetizers/$', menu_views.AppetizerList.as_view()),
    url(r'^menu/desserts/$', menu_views.DessertList.as_view()),
    url(r'^menu/ingredients/$', menu_views.IngredientList.as_view()),
    url(r'^register/$', user_views.register),
    url(r'^login/$', user_views.login_user),
    url(r'^logout/$', user_views.logout_user),
    url(r'^test/$', user_views.test_loggedin),
]

urlpatterns = format_suffix_patterns(urlpatterns)