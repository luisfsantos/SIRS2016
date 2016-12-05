from django.contrib.auth.decorators import login_required
from django.shortcuts import render
# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from Common.responses import createResponse
from Payments.serializers import PaymentSerializer
from Orders.models import Order

import requests
import json
from requests.auth import HTTPBasicAuth

@api_view(["GET", "POST"])
def paypalAPI(request):
    if request.method == 'POST':
        payment_serializer = PaymentSerializer(data=request.data)
        if payment_serializer.is_valid():
            order = Order.objects.get(identifier = payment_serializer.validated_data['identifier'])
            paymentID = payment_serializer.validated_data['paypal_confirm']['response_type']['id']
            paymentDATA = checkPayment(paymentID)

            state = paymentDATA.get('state')
            amount = paymentDATA.get('transactions').get('amount').get('total')
            currency = paymentDATA.get('transactions').get('amount').get('currency')
            completion_state = paymentDATA.get('related_resources').get('sale').get('state')
            if state == "approved" and order.price == float(amount) and currency == "EUR" and completion_state == "completed":
                order.status = 'PR'
                order.payment = 'CF'
                order.paypal_id = paymentID
                order.save()
                return Response(createResponse("Order", "Payment Processed"), status=status.HTTP_200_OK)
            else:
                return Response(createResponse("Order", "Invalid Payment"), status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response(createResponse("Order", payment_serializer.errors), status=status.HTTP_400_BAD_REQUEST)

    return Response(createResponse("Order", "Payment"))



def checkPayment(paymentID):
    url = "https://api.sandbox.paypal.com/v1/payments/payment/"+paymentID
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + requestAuthToken()}

    r = requests.get(url, headers=headers)
    return r.json()



def requestAuthToken():
    url = "https://api.sandbox.paypal.com/v1/oauth2/token"
    headers = {'Accept': 'application/json', 'Accept-Language': 'en_US', }
    data = 'grant_type=client_credentials'
    auth = HTTPBasicAuth('Ac46d01UTodbt_cVxaGtUgpRiBVAjGHcPGx2Q55LOx0A4qRHNVYSksBKwQUPgNX5aIkKUI24DM1G6nfV','EFwz3idXza8ETcrRWZ4t2_N1PYJDCpot4YT6rZxzYXUiZIh5zbQ8lYAZLiRtwZlTyjmN8fx3I6zT5fgO')

    r = requests.post(url, headers=headers, auth=auth, data = data)
    json_data = r.json()
    return json_data['access_token']