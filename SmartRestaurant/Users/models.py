from __future__ import unicode_literals

from django.db import models
from django.core.validators import RegexValidator
from django.contrib.auth.models import User
from django.db.models.signals import post_save
from django.dispatch import receiver

# Create your models here.

class UserProfile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    nif = models.IntegerField(null=True, unique=True, validators=[RegexValidator(r'^\d{9}$')], error_messages={'unique':"This nif has been used before"})

#User.customer=property(lambda u: Customer.objects.get_or_create(user=u)[0])

@receiver(post_save, sender=User)
def create_customer(sender, instance, created, **kwargs):
    if created:
        UserProfile.objects.create(user=instance)

@receiver(post_save, sender=User)
def save_customer(sender, instance, **kwargs):
    instance.userprofile.save()