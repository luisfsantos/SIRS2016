from django.contrib.auth.decorators import login_required
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import render

# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from Common.responses import createResponse
from Orders.models import Order, UserOrders
from Orders.serializers import OrderSerializer, ViewOrderSerializer, UserOrdersSerializer


@api_view(["POST", "GET"])
@login_required()
def order_requestAPI(request):

    if request.method == "POST":
        order_serializer = OrderSerializer(data=request.data)
        if order_serializer.is_valid():
            order = order_serializer.save()
            UserOrders.objects.create(user=request.user, order=order.identifier)
            return Response(createResponse("Order", order_serializer.data), status=status.HTTP_201_CREATED)
        else:
            return Response(createResponse("Order", order_serializer.errors), status=status.HTTP_400_BAD_REQUEST)

    else:
        if request.user.is_superuser:
            orders = Order.objects.all()
            order_serializer = ViewOrderSerializer(orders, many=True)
            return Response(order_serializer.data)
        else:
            return Response(createResponse("Order", "Request an order!"))

@api_view(["GET"])
@login_required()
def ordersAPI(request, orderid):
    if request.user.is_superuser:
        try:
            order = UserOrders.objects.get(order=orderid)
            serializer = UserOrdersSerializer(order)
            return Response(serializer.data)
        except UserOrders.DoesNotExist:
            return Response(createResponse("Order", "This order does not exist or has no information about customer"))
    else:
        return Response(createResponse("Order", "You do not have permission to view this resource"))
