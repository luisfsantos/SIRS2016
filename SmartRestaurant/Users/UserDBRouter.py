class AuthUserRouter(object):
    """
    A router to control all database operations on models in the
    auth application.
    """
    def db_for_read(self, model, **hints):
        """
        Attempts to read auth models go to users_db.
        """
        if model._meta.app_label == 'auth' or model._meta.app_label == 'Users':
            return 'users_db'
        return None

    def db_for_write(self, model, **hints):
        """
        Attempts to write auth models go to users_db.
        """
        if model._meta.app_label == 'auth' or model._meta.app_label == 'Users':
            return 'users_db'
        return None

    def allow_migrate(self, db, app_label, model_name=None, **hints):
        """
        Make sure the auth app only appears in the 'users_db'
        database.
        """
        if app_label == 'auth' or app_label == 'Users':
            return db == 'users_db'
        return None