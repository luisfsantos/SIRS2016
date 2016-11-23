from __future__ import unicode_literals

from django.db import models

# Create your models here.
class Ingredient(models.Model):
    name = models.CharField(max_length=100, blank=True, default='')


class Meal(models.Model):
    MAIN = 'MC'
    APPETIZER = 'AP'
    DESSERT = 'DE'
    MEAL_CHOICES = (
        (MAIN, 'Main Courses'),
        (APPETIZER, 'Appetizer'),
        (DESSERT, 'Dessert'),
    )

    name = models.CharField(max_length=100, blank=True, default='')
    meal_type = models.CharField(
        max_length=2,
        choices=MEAL_CHOICES,
        default=MAIN,
    )
    price = models.IntegerField()
    description = models.TextField()

    calories = models.IntegerField()
    ingredients = models.ManyToManyField(Ingredient, blank=True)

    class Meta:
        ordering = ('meal_type',)