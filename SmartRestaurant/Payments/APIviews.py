from django.contrib.auth.decorators import login_required
from django.shortcuts import render
# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from Common.responses import createResponse
from Payments.serializers import PaymentSerializer
from Orders.models import Order


import paypalrestsdk
paypalrestsdk.configure({
  "mode": "sandbox", # sandbox or live
  "client_id": "Ac46d01UTodbt_cVxaGtUgpRiBVAjGHcPGx2Q55LOx0A4qRHNVYSksBKwQUPgNX5aIkKUI24DM1G6nfV",
  "client_secret": "EFwz3idXza8ETcrRWZ4t2_N1PYJDCpot4YT6rZxzYXUiZIh5zbQ8lYAZLiRtwZlTyjmN8fx3I6zT5fgO" })

@api_view(["GET", "POST"])
@login_required()
def paypalAPI(request):
    if request.method == 'POST':
        payment_serializer = PaymentSerializer(data=request.data)
        if payment_serializer.is_valid():
            order = Order.objects.get(identifier = payment_serializer.validated_data['identifier'])
            paymentID = payment_serializer.validated_data['paypal_confirm']['response']['id']
            paymentDATA = paypalrestsdk.Payment.find(paymentID)
            state = paymentDATA.state
            amount = paymentDATA.transactions[0].amount.total
            currency = paymentDATA.transactions[0].amount.currency
            completion_state = paymentDATA.transactions[0].related_resources[0].sale.state
            #add error handling when things dont go as planed for each case
            if state == "approved" and float(order.price) == float(amount) and currency == "EUR" and completion_state == "completed":
                order.status = 'PR'
                order.payment = 'CF'
                order.paypal_id = paymentID
                order.save()
                createInvoice(paymentDATA, request.user)
                return Response(createResponse("Order", "Payment Processed"), status=status.HTTP_200_OK)
            else:
                return Response(createResponse("Order", "Invalid Payment"), status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response(createResponse("Order", payment_serializer.errors), status=status.HTTP_400_BAD_REQUEST)

    return Response(createResponse("Order", "Payment"))


def createInvoice(paymentDATA, user):
    merchant_info = {'email': 'smartrestaurant@restaurant.com', }
    if user.email != None:
        email = user.email
    else:
        email = paymentDATA.payer.payer_info.email
    billing_info = {"email": email, "first_name": user.first_name, "last_name": user.last_name}
    items = paymentDATA.transactions[0].item_list.items

    invoice = paypalrestsdk.Invoice(
        {'merchant_info': merchant_info, 'billing_info': billing_info, 'items': items})
    invoice.create()
    invoice.send()
