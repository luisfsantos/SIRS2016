"""
WSGI config for SmartRestaurant project.

It exposes the WSGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/1.10/howto/deployment/wsgi/
"""
import sys
import os

from django.core.wsgi import get_wsgi_application

apache_config= os.path.dirname(__file__)
project = os.path.dirname(apache_config)
workspace= os.path.dirname(project)
sys.path.append(workspace)
sys.path.append(project)

os.environ['PYTHON_EGG_CACHE'] = '/opt/.python-eggs'
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "SmartRestaurant.apache.override")

application = get_wsgi_application()
