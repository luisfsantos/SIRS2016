from django.contrib.auth.decorators import login_required
from django.shortcuts import render

# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from Common.responses import createResponse
from Orders.models import Order
from Orders.serializers import OrderSerializer


@api_view(["POST", "GET"])
@login_required()
def order_requestAPI(request):

    if request.method == "POST":
        order_serializer = OrderSerializer(data=request.data)
        if order_serializer.is_valid():
            order_serializer.save()
            return Response(createResponse("Order", order_serializer.data), status=status.HTTP_201_CREATED)
        else:
            return Response(createResponse("Order", order_serializer.errors), status=status.HTTP_400_BAD_REQUEST)

    else:
        orders = Order.objects.all()
        order_serializer = OrderSerializer(orders, many=True)
        return Response(order_serializer.data)
