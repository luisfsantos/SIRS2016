from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from Menu import APIviews as menu_views
from Users import APIviews as user_views

urlpatterns = [
    url(r'^menu/mains/$', menu_views.MainListAPI.as_view()),
    url(r'^menu/appetizers/$', menu_views.AppetizerListAPI.as_view()),
    url(r'^menu/desserts/$', menu_views.DessertListAPI.as_view()),
    url(r'^menu/ingredients/$', menu_views.IngredientListAPI.as_view()),
    url(r'^register/$', user_views.registerAPI),
    url(r'^login/$', user_views.login_userAPI),
    url(r'^logout/$', user_views.logout_userAPI),
    url(r'^test/$', user_views.test_loggedinAPI),
]

urlpatterns = format_suffix_patterns(urlpatterns)