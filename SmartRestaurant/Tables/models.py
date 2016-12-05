from __future__ import unicode_literals

from django.db import models
from django.contrib.auth.models import User
# Create your models here.


class TableModel(models.Model):
    user = models.ForeignKey(User, editable=True, null=True)
    #table_id = models.CharField(editable=False, unique=True, max_length=100)
    number_of_places = models.IntegerField()


