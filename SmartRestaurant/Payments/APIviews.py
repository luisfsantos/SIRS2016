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
def paypalAPI(request, id):
    if request.method == 'POST':
        payment_serializer = PaymentSerializer(data=request.data)
        if payment_serializer.is_valid():
            order = Order.objects.get(identifier = payment_serializer.validated_data['identifier'])


        else:
            return Response(createResponse("Order", payment_serializer.errors), status=status.HTTP_400_BAD_REQUEST)

    return Response(createResponse("testid", id))



def checkPayment(paymentID):
    url = "https://api.sandbox.paypal.com/v1/payments/payment/"+paymentID
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + requestAuthToken()}

    r = requests.get(url, headers=headers)
    json_data = r.json()
    return json_data['id']



def requestAuthToken():
    url = "https://api.sandbox.paypal.com/v1/oauth2/token"
    headers = {'Accept': 'application/json', 'Accept-Language': 'en_US', }
    data = {'grant_type':'client_credentials'}
    auth = HTTPBasicAuth('Ac46d01UTodbt_cVxaGtUgpRiBVAjGHcPGx2Q55LOx0A4qRHNVYSksBKwQUPgNX5aIkKUI24DM1G6nfV','EFwz3idXza8ETcrRWZ4t2_N1PYJDCpot4YT6rZxzYXUiZIh5zbQ8lYAZLiRtwZlTyjmN8fx3I6zT5fgO')

    r = requests.post(url, headers=headers, auth=auth, data = json.dumps(data))
    json_data = r.json()

    return json_data['access_token']