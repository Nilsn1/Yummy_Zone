package com.nilscreation.yummyzone.Models;

public class OrderDetails {

    int ItemsTotal, OrderPrice, DeliveryCharges;
    String OrderId;

    public OrderDetails(String OrderId, int itemsTotal, int deliveryCharges, int orderPrice) {
        ItemsTotal = itemsTotal;
        OrderPrice = orderPrice;
        DeliveryCharges = deliveryCharges;
        this.OrderId = OrderId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public int getItemsTotal() {
        return ItemsTotal;
    }

    public void setItemsTotal(int itemsTotal) {
        ItemsTotal = itemsTotal;
    }

    public int getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        OrderPrice = orderPrice;
    }

    public int getDeliveryCharges() {
        return DeliveryCharges;
    }

    public void setDeliveryCharges(int deliveryCharges) {
        DeliveryCharges = deliveryCharges;
    }
}
