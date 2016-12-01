from __future__ import unicode_literals

from django.db import models

# Create your models here.
from Menu.models import Meal


class OrderItem(models.Model):
    menu_item = models.ForeignKey(Meal)
    quantity = models.IntegerField(default=1)


class Order(models.Model):

    PENDING = 'PE'
    CONFIRMED = 'CF'
    CANCELED = 'CN'
    PROCESSING = 'PR'
    DELIVERED = 'DE'
    RECEIVED = 'RE'
    FETCH = 'FE'
    NONE = 'NO'

    STATUS_CHOICES = (
        (PENDING, 'Pending'),
        (PROCESSING, 'Processing'),
        (DELIVERED, 'Delivered'),
    )

    PAYMENT_CHOICES = (
        (PENDING, "Pending"),
        (CONFIRMED, "Confirmed"),
        (CANCELED, "Canceled")
    )

    CASH_CHOICES = (
        (NONE, "None"),
        (FETCH, "Fetch"),
        (RECEIVED, "Received")
    )

    identifier = models.CharField(max_length=32, blank=True)
    price = models.DecimalField(decimal_places=2, max_digits=7)
    order_items = models.ManyToManyField(OrderItem)
    payment = models.CharField(
        max_length=2,
        choices=PAYMENT_CHOICES,
        default=PENDING,
    )
    date_created = models.DateTimeField(auto_now_add=True)

    status = models.CharField(
        max_length=2,
        choices=STATUS_CHOICES,
        default=PENDING,
    )
    paypal_id = models.CharField(max_length=100, blank=True)
    cash = models.CharField(
        max_length=2,
        choices=CASH_CHOICES,
        default=NONE,
    )