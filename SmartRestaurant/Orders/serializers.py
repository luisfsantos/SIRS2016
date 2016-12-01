from rest_framework import serializers

from Orders.models import OrderItem, Order


class OrderItemSerializer(serializers.ModelSerializer):

    menu_item_id = serializers.ReadOnlyField(source='menu_item.id')

    class Meta:
        model = OrderItem
        fields = ('menu_item_id', 'quantity')

    def create(self, validated_data):
        item = OrderItem(
            quantity = validated_data['quantity'],
            menu_item = validated_data['menu_item'],
        )
        item.save()
        return item

class OrderSerializer(serializers.ModelSerializer):
    order_items = OrderItemSerializer(read_only=True, many=True)

    class Meta:
        model = Order
        fields = ('identifier', 'order_items', 'price', 'date_created', 'payment','status', 'paypal_id', 'cash')
        extra_kwargs = {'identifier': {'read_only': True}}

    def create(self, validated_data):
        items = OrderItemSerializer(data=validated_data['order_items'], many=True)
        items.save()
        order = Order(
            price= validated_data['price'],
        )
        order.save()
        order.order_items.add(*[items])
        return order