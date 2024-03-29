from django.contrib.auth.decorators import login_required
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import render

# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view, throttle_classes
from rest_framework.response import Response

from Common.responses import createResponse
from Common.throttle import DailyAPIThrottle, BurstAPIThrottle, OrderAPIThrottle
from Orders.models import Order, UserOrders
from Orders.serializers import OrderSerializer, ViewOrderSerializer, UserOrdersSerializer, CancelOrderSerializer


@api_view(["POST", "GET"])
@throttle_classes([DailyAPIThrottle, BurstAPIThrottle, OrderAPIThrottle])
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


@api_view(["POST", "GET"])
@throttle_classes([DailyAPIThrottle, BurstAPIThrottle])
@login_required()
def order_cancelAPI(request):
    if request.method == "POST":
        cancel_serializer = CancelOrderSerializer(data=request.data)
        if cancel_serializer.is_valid():
            try:
                user_order = UserOrders.objects.get(user=request.user,
                                                    order=cancel_serializer.validated_data['identifier'])
                order = Order.objects.get(identifier=user_order.order)
                order.payment = 'CN'
                order.status = 'AR'
                user_order.delete()
                order.save()
                return Response(createResponse("Order", "Order Canceled"), status=status.HTTP_200_OK)
            except UserOrders.DoesNotExist:
                return Response(createResponse("Order", "That order does not exist or you cannot cancel it."), status=status.HTTP_400_BAD_REQUEST)

        else:
            return Response(createResponse("Order", cancel_serializer.errors), status=status.HTTP_400_BAD_REQUEST)

    else:
        return Response(createResponse("Order", "Cancel an order"))


@api_view(["GET"])
@throttle_classes([DailyAPIThrottle, BurstAPIThrottle])
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
