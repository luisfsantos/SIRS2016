# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2016-11-27 00:18
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Menu', '0002_auto_20161126_1101'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='ingredient',
            options={'ordering': ('name',)},
        ),
    ]