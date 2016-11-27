from django.shortcuts import render
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from Menu.models import Meal, Ingredient
from Menu.serializers import MealSerializer, IngredientSerializer
from django.contrib.auth.decorators import login_required
from django.utils.decorators import method_decorator
# Create your views here.
class MainList(APIView):
    """
    List all mainCourses, or create a new mainCourse.
    """
    @method_decorator(login_required)
    def get(self, request, format=None):
        try:
            meal = Meal.objects.filter(meal_type=Meal.MAIN)
        except Meal.DoesNotExist:
            meal = None
        serializer = MealSerializer(meal, many=True)
        return Response(serializer.data)

    @method_decorator(login_required)
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

    @method_decorator(login_required)
    def get(self, request, format=None):
        try:
            meal = Meal.objects.filter(meal_type=Meal.APPETIZER)
        except Meal.DoesNotExist:
            meal = None
        serializer = MealSerializer(meal, many=True)
        return Response(serializer.data)

    @method_decorator(login_required)
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

    @method_decorator(login_required)
    def get(self, request, format=None):
        try:
            meal = Meal.objects.filter(meal_type=Meal.DESSERT)
        except Meal.DoesNotExist:
            meal = None
        serializer = MealSerializer(meal, many=True)
        return Response(serializer.data)

    @method_decorator(login_required)
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

    @method_decorator(login_required)
    def get(self, request, format=None):
        ingredient = Ingredient.objects.all()
        serializer = IngredientSerializer(ingredient, many=True)
        return Response(serializer.data)

    @method_decorator(login_required)
    def post(self, request, format=None):
        ingredient = IngredientSerializer(data=request.data)
        if ingredient.is_valid():
            ingredient.save()
            return Response(ingredient.data, status=status.HTTP_201_CREATED)
        return Response(ingredient.errors, status=status.HTTP_400_BAD_REQUEST)
