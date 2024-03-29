
"""SmartRestaurant URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin
from django.views.generic import RedirectView

urlpatterns = [
    url(r'^favicon\.ico$', RedirectView.as_view(url='/media/images/favicon.ico')),
    url(r'^admin/', admin.site.urls),
    url(r'^', include('Users.urls')),
    url(r'^menu/', include('Menu.urls')),
    url(r'^api/', include('AndroidAPI.urls')),
    url(r'^pos/', include('POS.urls')),
    url(r'^tables/', include('Tables.urls')),
]
