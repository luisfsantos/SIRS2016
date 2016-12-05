from rest_framework import serializers



class TableSerializer(serializers.Serializer):
    table_id = serializers.CharField()