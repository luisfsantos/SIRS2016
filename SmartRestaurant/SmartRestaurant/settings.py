"""
Django settings for SmartRestaurant project.

Generated by 'django-admin startproject' using Django 1.10.3.

For more information on this file, see
https://docs.djangoproject.com/en/1.10/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/1.10/ref/settings/
"""

import os

# Build paths inside the project like this: os.path.join(BASE_DIR, ...)
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))


# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/1.10/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = '9(%lx-(^uzjb#zknx16qzmj^7o1-#&&09os#=!1116z7a8%ipv'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

ALLOWED_HOSTS = []


# Application definition

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'rest_framework',
    'Menu',
    'Users',
    'AndroidAPI',
    'Payments',
    'Orders',
    'Tables',
    'POS',
]

MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

ROOT_URLCONF = 'SmartRestaurant.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'SmartRestaurant.wsgi.application'
LOGIN_URL ='/login/'
PRODUCTION = False

# Database
# https://docs.djangoproject.com/en/1.10/ref/settings/#databases


DATABASES = {
    'default': {
        'NAME': 'smartrestaurant_data',
        'ENGINE': 'django.db.backends.mysql',
        'USER': 'SmartRestaurant',
        'PASSWORD': 'skqDKG4dkD456Dsja2DC6X'
    }
}

# Password validation
# https://docs.djangoproject.com/en/1.10/ref/settings/#auth-password-validators

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]


# Internationalization
# https://docs.djangoproject.com/en/1.10/topics/i18n/

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True


# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/1.10/howto/static-files/

STATIC_URL = '/static/'

#
# LOGGING = {
#     'version': 1,
#     'disable_existing_loggers': False,
#     'filters': {
#         'require_debug_false': {
#             '()': 'django.utils.log.RequireDebugFalse',
#         },
#         'require_debug_true': {
#             '()': 'django.utils.log.RequireDebugTrue',
#         },
#     },
#     'formatters': {
#         'simple': {
#             'format': '[%(asctime)s] %(levelname)s %(message)s',
#             'datefmt': '%Y-%m-%d %H:%M:%S'
#         },
#         'verbose': {
#             'format': '[%(asctime)s] %(levelname)s [%(name)s.%(funcName)s:%(lineno)d] %(message)s',
#             'datefmt': '%Y-%m-%d %H:%M:%S'
#         },
#     },
#     'handlers': {
#         'console': {
#             'level': 'DEBUG',
#             'filters': ['require_debug_true'],
#             'class': 'logging.StreamHandler',
#             'formatter': 'simple'
#         },
#         'development_logfile': {
#             'level': 'INFO',
#             'filters': ['require_debug_true'],
#             'class': 'logging.FileHandler',
#             'filename': '/tmp/django_dev.log',
#             'formatter': 'verbose'
#         },
#         'logfile_smart': {
#             'level': 'INFO',
#             'filters': ['require_debug_false'],
#             'class': 'logging.FileHandler',
#             'filename': '/opt/django_smartrestaurant.log',
#             'formatter': 'verbose'
#         },
#         'production_logfile': {
#             'level': 'ERROR',
#             'filters': ['require_debug_false'],
#             'class': 'logging.FileHandler',
#             'filename': '/var/log/django_production.log',
#             'formatter': 'simple'
#         },
#         'dba_logfile': {
#             'level': 'DEBUG',
#             'filters': ['require_debug_false', 'require_debug_true'],
#             'class': 'logging.FileHandler',
#             'filename': '/var/log/django_dba.log',
#             'formatter': 'simple'
#         },
#     },
#     'loggers': {
#         'POS': {
#             'handlers': ['console', 'development_logfile', 'logfile_smart', 'production_logfile'],
#         },
#         'Tables': {
#             'handlers': ['console', 'development_logfile', 'logfile_smart', 'production_logfile'],
#         },
#         'django': {
#             'handlers': ['console', 'development_logfile', 'logfile_smart', 'production_logfile'],
#         },
#         'py.warnings': {
#             'handlers': ['console', 'development_logfile'],
#         },
#     }
# }
