from rest_framework import serializers
from Users.models import Customer
from django.contrib.auth.models import User

class CustomerSerializer(serializers.ModelSerializer):
    class Meta:
        model = Customer
        fields = ('nif')

class UserSerializer(serializers.ModelSerializer):
    nif = serializers.IntegerField(source='customer.nif')

    class Meta:
        model = User
        fields = ('email', 'username', 'nif', 'password')
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, validated_data):
        user = User(
            email=validated_data['email'],
            username=validated_data['username']
        )
        user.set_password(validated_data['password'])
        user.save()
        user.customer.nif = validated_data['customer']['nif']
        user.save()
        return user
