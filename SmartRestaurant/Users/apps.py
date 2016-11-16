from __future__ import unicode_literals

from django.apps import AppConfig


class UsersConfig(AppConfig):
    name = 'Users'

class ProfilesConfig(AppConfig):
    name = 'SmartRestaurant.Users'
    verbose_name = _('customer')

    def ready(self):
        import Users.signals