from SmartRestaurant.settings import *

DEBUG = False
ALLOWED_HOSTS = ['www.smartrestaurant.xyz', 'smartrestaurant.xyz']
PRODUCTION = True

LOGGING = {
    'version': 1,
    'disable_existing_loggers': False,
    'filters': {
        'require_debug_false': {
            '()': 'django.utils.log.RequireDebugFalse',
        },
        'require_debug_true': {
            '()': 'django.utils.log.RequireDebugTrue',
        },
    },
    'formatters': {
        'simple': {
            'format': '[%(asctime)s] %(levelname)s %(message)s',
            'datefmt': '%Y-%m-%d %H:%M:%S'
        },
        'verbose': {
            'format': '[%(asctime)s] %(levelname)s [%(name)s.%(funcName)s:%(lineno)d] %(message)s',
            'datefmt': '%Y-%m-%d %H:%M:%S'
        },
    },
    'handlers': {
        'console': {
            'level': 'DEBUG',
            'filters': ['require_debug_true'],
            'class': 'logging.StreamHandler',
            'formatter': 'simple'
        },
        'development_logfile': {
            'level': 'DEBUG',
            'filters': ['require_debug_true'],
            'class': 'logging.FileHandler',
            'filename': '/tmp/django_dev.log',
            'formatter': 'verbose'
        },
        'logfile_smart': {
            'level': 'DEBUG',
            'filters': ['require_debug_false'],
            'class': 'logging.FileHandler',
            'filename': '/opt/django_smartrestaurant.log',
            'formatter': 'verbose'
        },
        'production_logfile': {
            'level': 'ERROR',
            'filters': ['require_debug_false'],
            'class': 'logging.FileHandler',
            'filename': '/var/log/django_production.log',
            'formatter': 'simple'
        },
        'dba_logfile': {
            'level': 'DEBUG',
            'filters': ['require_debug_false', 'require_debug_true'],
            'class': 'logging.FileHandler',
            'filename': '/var/log/django_dba.log',
            'formatter': 'simple'
        },
    },
    'loggers': {
        'POS': {
            'handlers': ['console', 'development_logfile', 'logfile_smart', 'production_logfile'],
        },
        'Tables': {
            'handlers': ['console', 'development_logfile', 'logfile_smart', 'production_logfile'],
        },
        'dba': {
            'handlers': ['console', 'dba_logfile'],
        },
        'django': {
            'handlers': ['console', 'development_logfile', 'logfile_smart', 'production_logfile'],
        },
        'py.warnings': {
            'handlers': ['console', 'development_logfile'],
        },
    }
}
