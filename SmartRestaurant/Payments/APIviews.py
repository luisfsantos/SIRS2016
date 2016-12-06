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
import ssl
import time


import paypalrestsdk
paypalrestsdk.configure({
  "mode": "sandbox", # sandbox or live
  "client_id": "Ac46d01UTodbt_cVxaGtUgpRiBVAjGHcPGx2Q55LOx0A4qRHNVYSksBKwQUPgNX5aIkKUI24DM1G6nfV",
  "client_secret": "EFwz3idXza8ETcrRWZ4t2_N1PYJDCpot4YT6rZxzYXUiZIh5zbQ8lYAZLiRtwZlTyjmN8fx3I6zT5fgO" })

@api_view(["GET", "POST"])
def paypalAPI(request):
    if request.method == 'POST':
        payment_serializer = PaymentSerializer(data=request.data)
        if payment_serializer.is_valid():
            order = Order.objects.get(identifier = payment_serializer.validated_data['identifier'])
            paymentID = payment_serializer.validated_data['paypal_confirm']['response']['id']
            paymentDATA = paypalrestsdk.Payment.find(paymentID)
            state = paymentDATA.state
            transactions = paymentDATA.transactions
            transactions1 = paymentDATA.transactions[0]

            amount_2 = paymentDATA.transactions[0].amount
            amount_3 = transactions1.amount
            amount_d = paymentDATA.transactions[0].get('amount')
            amount = paymentDATA.transactions[0].get('amount').get('total')
            currency = paymentDATA.transactions[0].get('amount').get('currency')
            completion_state = paymentDATA.transactions[0].get('related_resources')[0].get('sale').get('state')
            #add error handling when things dont go as planed for each case
            if state == "approved" and float(order.price) == float(amount) and currency == "EUR" and completion_state == "completed":
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
    url = "https://api-3t.sandbox.paypal.com/v1/payments/payment/"+paymentID
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + requestAuthToken()}

    r = requests.get(url, headers=headers)
    return r.json()



def requestAuthToken():
    url = "https://api-3t.sandbox.paypal.com/v1/oauth2/token"
    headers = {'Accept': 'application/json', 'Accept-Language': 'en_US', }
    data = 'grant_type=client_credentials'
    auth = HTTPBasicAuth('Ac46d01UTodbt_cVxaGtUgpRiBVAjGHcPGx2Q55LOx0A4qRHNVYSksBKwQUPgNX5aIkKUI24DM1G6nfV','EFwz3idXza8ETcrRWZ4t2_N1PYJDCpot4YT6rZxzYXUiZIh5zbQ8lYAZLiRtwZlTyjmN8fx3I6zT5fgO')
    try:
        r = requests.post(url, headers=headers, auth=auth, data = data)
    except requests.exceptions.SSLError:
        time.sleep(5)
        r = requests.post(url, headers=headers, auth=auth, data=data)
    json_data = r.json()
    return json_data['access_token']
