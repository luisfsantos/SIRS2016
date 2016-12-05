from django.conf.urls import url, include

from Menu import APIviews as menu_views
from Orders import APIviews as order_views
from Users import APIviews as user_views
from Tables import APIviews as table_views

urlpatterns = [
    url(r'^menu/mains/$', menu_views.MainListAPI.as_view()),
    url(r'^menu/appetizers/$', menu_views.AppetizerListAPI.as_view()),
    url(r'^menu/desserts/$', menu_views.DessertListAPI.as_view()),
    url(r'^menu/ingredients/$', menu_views.IngredientListAPI.as_view()),
    url(r'^register/$', user_views.registerAPI),
    url(r'^login/$', user_views.login_userAPI),
    url(r'^logout/$', user_views.logout_userAPI),
    url(r'^test/$', user_views.test_loggedinAPI),
    url(r'^order/request/$', order_views.order_requestAPI),
    url(r'^order/cancel/$', order_views.order_cancelAPI),
    url(r'^order/view/(?P<orderid>[^/]+)/$', order_views.ordersAPI),
    url(r'^table/verify/$', table_views.verifyTableAPI),
    url(r'^payments/pay/(?P<id>[0-9a-zA-Z]{32})/', include('Payments.urls')),
]

# urlpatterns = format_suffix_patterns(urlpatterns)