from django.contrib.auth.decorators import login_required
from django.shortcuts import render

# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from Common.responses import createResponse
from Orders.serializers import OrderSerializer



@api_view(["GET", "POST"])
def abcd(request, id):
    return Response(createResponse("testid", id))
