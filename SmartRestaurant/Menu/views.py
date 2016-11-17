from django.shortcuts import render
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from Menu.models import Meal, Ingredient
from Menu.serializers import MealSerializer, IngredientSerializer
# Create your views here.
class MainList(APIView):
    """
    List all mainCourses, or create a new mainCourse.
    """
    def get(self, request, format=None):
        try:
            meal = Meal.objects.get(meal_type=Meal.MAIN)
        except Meal.DoesNotExist:
            meal = None
        serializer = MealSerializer(meal, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        meal = MealSerializer(data=request.data)
        if meal.is_valid():
            meal.save()
            return Response(meal.data, status=status.HTTP_201_CREATED)
        return Response(meal.errors, status=status.HTTP_400_BAD_REQUEST)

class AppetizerList(APIView):
    """
    List all appetizers, or create a new appetizer.
    """
    def get(self, request, format=None):
        meal = Meal.objects.get(meal_type=Meal.APPETIZER)
        serializer = MealSerializer(meal, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        meal = MealSerializer(data=request.data)
        if meal.is_valid():
            meal.save()
            return Response(meal.data, status=status.HTTP_201_CREATED)
        return Response(meal.errors, status=status.HTTP_400_BAD_REQUEST)

class DessertList(APIView):
    """
    List all desserts, or create a new dessert.
    """
    def get(self, request, format=None):
        meal = Meal.objects.get(meal_type=Meal.DESSERT)
        serializer = MealSerializer(meal, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        meal = MealSerializer(data=request.data)
        if meal.is_valid():
            meal.save()
            return Response(meal.data, status=status.HTTP_201_CREATED)
        return Response(meal.errors, status=status.HTTP_400_BAD_REQUEST)


class IngredientList(APIView):
    """
    List all ingredients, or create a new ingredient.
    """
    def get(self, request, format=None):
        ingredient = Ingredient.objects.all()
        serializer = IngredientSerializer(ingredient, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        ingredient = IngredientSerializer(data=request.data)
        if ingredient.is_valid():
            ingredient.save()
            return Response(ingredient.data, status=status.HTTP_201_CREATED)
        return Response(ingredient.errors, status=status.HTTP_400_BAD_REQUEST)
