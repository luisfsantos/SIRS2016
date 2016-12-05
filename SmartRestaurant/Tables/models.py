from __future__ import unicode_literals

from django.db import models
from django.contrib.auth.models import User
# Create your models here.


class TableModel(models.Model):
    user = models.ForeignKey(User, editable=True, null=True, related_name='table')
    table_id = models.CharField(editable=False, unique=True, max_length=100)
    number = models.IntegerField()
    number_of_places = models.IntegerField()

    def clearTable(self):
        self.user = None
        self.save()


