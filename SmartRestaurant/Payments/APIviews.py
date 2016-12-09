from django.contrib.auth.decorators import login_required
from django.shortcuts import render
# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view, throttle_classes
from rest_framework.response import Response

from Common.responses import createResponse
from Common.throttle import DailyAPIThrottle, BurstAPIThrottle
from Payments.serializers import PaymentSerializer
from Orders.models import Order, UserOrders

import paypalrestsdk

paypalrestsdk.configure({
    "mode": "sandbox",  # sandbox or live
    "client_id": "Ac46d01UTodbt_cVxaGtUgpRiBVAjGHcPGx2Q55LOx0A4qRHNVYSksBKwQUPgNX5aIkKUI24DM1G6nfV",
    "client_secret": "EFwz3idXza8ETcrRWZ4t2_N1PYJDCpot4YT6rZxzYXUiZIh5zbQ8lYAZLiRtwZlTyjmN8fx3I6zT5fgO"})


@api_view(["GET", "POST"])
@login_required()
@throttle_classes([DailyAPIThrottle, BurstAPIThrottle])
def paypal_api(request):

    def verify_order(paymentDATA, order):
        state = paymentDATA.state
        amount = paymentDATA.transactions[0].amount.total
        currency = paymentDATA.transactions[0].amount.currency
        completion_state = paymentDATA.transactions[0].related_resources[0].sale.state
        # add error handling when things dont go as planed for each case
        if state == "approved" and float(order.price) == float(
                amount) and currency == "EUR" and completion_state == "completed":
            order.status = 'PR'
            order.payment = 'CF'
            order.paypal_id = paymentID
            order.save()
            # createInvoice(paymentDATA, request.user)
            return Response(createResponse("Order", "Payment Processed"), status=status.HTTP_200_OK)
        else:
            order.payment = 'FR'
            order.paypal_id = paymentID
            order.save()
            #UserOrders.objects.get(order=order.identifier).delete()
            return Response(createResponse("Order", "Invalid Payment"), status=status.HTTP_400_BAD_REQUEST)


    if request.method == 'POST':
        payment_serializer = PaymentSerializer(data=request.data)
        if payment_serializer.is_valid():
            try:
                user_order = UserOrders.objects.get(user=request.user,
                                                    order=payment_serializer.validated_data['identifier'])
                order = Order.objects.get(identifier=user_order.order)
                paymentID = payment_serializer.validated_data['paypal_confirm']['response']['id']
                try:
                    paid_order = Order.objects.get(paypal_id=paymentID)
                    if paid_order == order:
                        return Response(createResponse("Order", "Payment has already been processed"), status=status.HTTP_200_OK)
                    else:
                        return Response(createResponse("Order", "This payment has been used!"), status=status.HTTP_400_BAD_REQUEST)
                except Order.DoesNotExist:
                    paymentDATA = paypalrestsdk.Payment.find(paymentID)
                    return verify_order(paymentDATA, order)
            except UserOrders.DoesNotExist:
                return Response(createResponse("Order", "That order does not exist or you cannot pay for it"),
                                status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response(createResponse("Order", payment_serializer.errors), status=status.HTTP_400_BAD_REQUEST)

    return Response(createResponse("Order", "Payment"))


def createInvoice(paymentDATA, user):
    merchant_info = {'email': 'smartrestaurant@restaurant.com', }
    email = paymentDATA.payer.payer_info.email
    billing_info = {"email": email, "first_name": user.first_name, "last_name": user.last_name}
    items = paymentDATA.transactions[0].item_list.items
    items_info = []
    for item in items:
        item_info = {'name': item.name, 'quantity': item.quantity,
                     'unit_price': {"currency": item.currency, "value": item.price}}
        items_info.append(item_info)

    invoice = paypalrestsdk.Invoice(
        {'merchant_info': merchant_info, 'billing_info': [billing_info, ], 'items': items_info})
    created = invoice.create()
    if created:
        invoice.send()
    return created

