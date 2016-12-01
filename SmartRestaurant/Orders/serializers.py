import random
import string

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
            menu_item = Meal.objects.get(pk=validated_data['menu_item']['id']),
        )
        item.save()
        return item

class OrderSerializer(serializers.ModelSerializer):
    order_items = OrderItemSerializer(many=True)

    class Meta:
        model = Order
        fields = ('identifier', 'order_items', 'price')
        extra_kwargs = {'identifier': {'read_only': True}, 'price': {'read_only': True}}

    def create(self, validated_data):
        with transaction.atomic():
            total_price = 0
            order = Order.objects.create(
                price=0,
                identifier=self.id_generator()
            )
            for order_item in validated_data['order_items']:
                item = OrderItem.objects.create(
                    quantity=order_item['quantity'],
                    menu_item=Meal.objects.get(pk=order_item['menu_item']['id']),
                )
                total_price += Meal.objects.get(pk=order_item['menu_item']['id']).price * order_item['quantity']
                order.order_items.add(item)
            order.price = total_price
            return order

    def id_generator(self, size=32, chars=string.ascii_letters + string.digits):
        return ''.join(random.SystemRandom().choice(chars) for _ in range(size))

class ViewOrderSerializer(serializers.ModelSerializer):
    order_items = OrderItemSerializer(many=True)

    class Meta:
        model = Order
        fields = ('identifier', 'order_items', 'price', 'date_created', 'payment','status', 'paypal_id', 'cash')
