from __future__ import unicode_literals

from django.db import models

def upload_to(instance, filename):
    return 'menu_image/{}/{}'.format(instance.name, filename)

# Create your models here.
class Ingredient(models.Model):
    name = models.CharField(max_length=100, blank=True, default='')
    class Meta:
        ordering = ('name',)

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
    price = models.FloatField()
    description = models.TextField()

    calories = models.IntegerField()
    ingredients = models.ManyToManyField(Ingredient, blank=True)
    image = models.ImageField(blank=True, upload_to=upload_to)

    class Meta:
        ordering = ('meal_type',)