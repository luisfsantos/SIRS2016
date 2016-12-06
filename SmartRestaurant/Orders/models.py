from __future__ import unicode_literals
import uuid
from django.db import models

# Create your models here.
from Menu.models import Meal
from django.contrib.auth.models import User
from Tables.models import TableModel


class UserOrders(models.Model):
    user = models.ForeignKey(User, null=True)
    order = models.UUIDField(editable=False, unique=True)

class OrderItem(models.Model):
    menu_item = models.ForeignKey(Meal)
    quantity = models.IntegerField(default=1)


class Order(models.Model):

    PENDING = 'PE'
    ARCHIVED = 'AR'
    CONFIRMED = 'CF'
    CANCELED = 'CN'
    PROCESSING = 'PR'
    DELIVERED = 'DE'
    RECEIVED = 'RE'
    FETCH = 'FE'
    NONE = 'NO'
    CASH = 'CA'
    PAYPAL = 'PP'

    STATUS_CHOICES = (
        (PENDING, 'Pending'),
        (PROCESSING, 'Processing'),
        (DELIVERED, 'Delivered'),
        (ARCHIVED, 'Archived'),
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

    METHOD_CHOICES= (
        (CASH, "Cash"),
        (PAYPAL, "PayPal")
    )

    identifier = models.UUIDField(default=uuid.uuid4, editable=False, unique=True)
    table = models.ForeignKey(TableModel, null=True)
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
    payment_method = models.CharField(
        max_length=2,
        choices=METHOD_CHOICES,
    )
    paypal_id = models.CharField(max_length=100, blank=True)
    cash = models.CharField(
        max_length=2,
        choices=CASH_CHOICES,
        default=NONE,
    )