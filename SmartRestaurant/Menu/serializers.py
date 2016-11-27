from rest_framework import serializers
from Menu.models import Ingredient, Meal
from django.contrib.auth.models import User

class IngredientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ingredient
        fields = ('name',)

    def create(self, validated_data):
        ingredient = Ingredient(
            name= validated_data['name'],
        )
        ingredient.save()
        return ingredient

class MealSerializer(serializers.ModelSerializer):
    ingredients = IngredientSerializer(read_only=True, many=True)

    class Meta:
        model = Meal
        fields = ('id', 'name', 'meal_type', 'price', 'description', 'calories', 'ingredients', 'image')
        extra_kwargs = {'id': {'read_only': True}}

    def create(self, validated_data):
        meal = Meal(
            meal_type=validated_data['meal_type'],
            name= validated_data['name'],
            price=validated_data['price'],
            description= validated_data['description'],
            calories= validated_data['calories']
        )
        meal.save()
        return meal
