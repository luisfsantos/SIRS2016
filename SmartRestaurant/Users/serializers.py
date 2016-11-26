from rest_framework import serializers
from Users.models import UserProfile
from django.contrib.auth.models import User

class ProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('nif')
        extra_kwargs = {"nif": {"error_messages": {"required": "The nif is a required entry"}}}

class UserSerializer(serializers.ModelSerializer):
    nif = serializers.IntegerField(source='userprofile.nif')

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

        new_nif = validated_data['userprofile']['nif']
        if UserProfile.objects.filter(nif=new_nif).exists() == True:
            raise serializers.ValidationError({'nif': ["This NIF has already been used",]})
        else:
            user.save()
            user.userprofile.nif = new_nif
        user.save()
        return user
