# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2016-11-26 11:01
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Users', '0005_auto_20161126_1055'),
    ]

    operations = [
        migrations.AlterField(
            model_name='userprofile',
            name='nif',
            field=models.IntegerField(max_length=9, null=True, unique=True),
        ),
    ]
