from rest_framework import serializers

from Orders.models import OrderItem, Order, UserOrders
from Menu.models import Meal
from Users.serializers import UserSerializer
from django.db import transaction
from Tables.models import TableModel


class OrderItemSerializer(serializers.ModelSerializer):

    menu_item_id = serializers.IntegerField(source='menu_item.id')
    item_name = serializers.CharField(source='menu_item.name', required=False, read_only=True)
    price = serializers.DecimalField(source='menu_item.price', decimal_places=2, max_digits=7, required=False, read_only=True)
    class Meta:
        model = OrderItem
        fields = ('menu_item_id', 'item_name', 'quantity', 'price')

    def create(self, validated_data):
        item = OrderItem(
            quantity = validated_data['quantity'],
            menu_item = Meal.objects.get(pk=validated_data['menu_item']['id']),
        )
        item.save()
        return item

class OrderSerializer(serializers.ModelSerializer):
    order_items = OrderItemSerializer(many=True)
    table_id = serializers.CharField(source='table.table_id')
    class Meta:
        model = Order
        fields = ('identifier', 'order_items', 'price', 'table_id')
        extra_kwargs = {'identifier': {'read_only': True}, 'price': {'read_only': True}}

    def create(self, validated_data):
        with transaction.atomic():
            total_price = 0
            order = Order.objects.create(
                price=0,
            )
            for order_item in validated_data['order_items']:
                item = OrderItem.objects.create(
                    quantity=order_item['quantity'],
                    menu_item=Meal.objects.get(pk=order_item['menu_item']['id']),
                )
                total_price += Meal.objects.get(pk=order_item['menu_item']['id']).price * order_item['quantity']
                order.order_items.add(item)
            order.table = TableModel.objects.get(table_id=validated_data['table_id'])
            order.price = total_price
            order.save()
            return order

class ViewOrderSerializer(serializers.ModelSerializer):
    order_items = OrderItemSerializer(many=True)

    class Meta:
        model = Order
        fields = ('identifier', 'order_items', 'price', 'date_created', 'payment','status', 'paypal_id', 'cash')

class UserOrdersSerializer(serializers.ModelSerializer):
    owner = UserSerializer(source='user')
    order = serializers.SerializerMethodField()

    def get_order(self, userorder):
        return ViewOrderSerializer(Order.objects.get(identifier= userorder.order)).data

    class Meta:
        model = UserOrders
        fields = ('order', 'owner')
