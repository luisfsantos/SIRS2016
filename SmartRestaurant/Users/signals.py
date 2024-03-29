from django.contrib.auth.models import User
from django.db.models.signals import post_save
from django.dispatch import receiver

from Users.models import UserProfile

@receiver(post_save, sender=User)
def create_customer(sender, instance, created, **kwargs):
    if created:
        UserProfile.objects.create(user=instance)

@receiver(post_save, sender=User)
def save_customer(sender, instance, **kwargs):
    instance.customer.save()