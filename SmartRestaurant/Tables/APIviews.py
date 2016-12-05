from django.contrib.auth.decorators import login_required

# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from Tables.serializers import TableSerializer
from Tables.models import TableModel
from Common.responses import createResponse


@api_view(["POST", "GET"])
@login_required()
def verifyTableAPI(request):
    if request.method == "POST":
        table_serializer = TableSerializer(data=request.data)
        if table_serializer.is_valid():
            try:
                table = TableModel.objects.get(table_id=table_serializer.validated_data['table_id'])
                if (table.user is None) or (table.user == request.user):
                    table.user = request.user
                    table.save()
                    return Response(createResponse("Table", table_serializer.data), status=status.HTTP_200_OK)
                else:
                    return Response(createResponse("Table", "The table is occupied"), status=status.HTTP_400_BAD_REQUEST) #this can be a covert channel to check how full a restaurant is
            except TableModel.DoesNotExist:
                return Response(createResponse("Table", "That table does not exist"), status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response(createResponse("Table", table_serializer.errors), status=status.HTTP_400_BAD_REQUEST)

    else:
        return Response(createResponse("Table", "Sit down at a table!"), status=status.HTTP_400_BAD_REQUEST)
