from rest_framework import serializers


class PayPalResponseSerializer(serializers.Serializer):
    state = serializers.CharField()
    id = serializers.CharField()
    create_time = serializers.CharField()
    intent = serializers.CharField()

class PaypalPaymentSerializer(serializers.Serializer):
    response = PayPalResponseSerializer()
    response_type = serializers.CharField()


class PaymentSerializer(serializers.Serializer):
    identifier = serializers.UUIDField()
    paypal_confirm = PaypalPaymentSerializer()