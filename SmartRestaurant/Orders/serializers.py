from rest_framework import serializers

from Orders.models import OrderItem, Order
from Menu.models import Meal
from django.db import transaction


class OrderItemSerializer(serializers.ModelSerializer):

    menu_item_id = serializers.IntegerField(source='menu_item.id')

    class Meta:
        model = OrderItem
        fields = ('menu_item_id', 'quantity')

    def create(self, validated_data):
        item = OrderItem(
            quantity = validated_data['quantity'],
            menu_item = Meal.objects.get(pk=validated_data['menu_item_id']),
        )
        item.save()
        return item

class OrderSerializer(serializers.ModelSerializer):
    order_items = OrderItemSerializer(many=True)

    class Meta:
        model = Order
        fields = ('identifier', 'order_items', 'price', 'date_created', 'payment','status', 'paypal_id', 'cash')
        extra_kwargs = {'identifier': {'read_only': True}}

    def create(self, validated_data):
        with transaction.atomic():
            order = Order.objects.create(
                price=validated_data['price'],
            )
            for order_item in validated_data['order_items']:
                item = OrderItem.objects.create(
                    quantity=order_item['quantity'],
                    menu_item=Meal.objects.get(pk=order_item['menu_item']['id']),
                )
                order.order_items.add(item)

            return order