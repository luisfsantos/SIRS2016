from __future__ import unicode_literals

from django.apps import AppConfig
from django.utils.translation import ugettext_lazy as _

class UsersConfig(AppConfig):
    name = 'Users'

class ProfilesConfig(AppConfig):
    name = 'SmartRestaurant.Users'
    verbose_name = _('Users')

    def ready(self):
        import Users.signals