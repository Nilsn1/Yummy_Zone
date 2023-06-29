package com.nilscreation.yummyzone.Models;

public class OrderDetails {

    int ItemsTotal, OrderPrice, DeliveryCharges;
    String OrderId;

    String Address, Time;

    public OrderDetails() {

    }

    public OrderDetails(String OrderId, int itemsTotal, int deliveryCharges, int orderPrice) {
        ItemsTotal = itemsTotal;
        OrderPrice = orderPrice;
        DeliveryCharges = deliveryCharges;
        this.OrderId = OrderId;
    }

    public OrderDetails(String orderId, int itemsTotal, int deliveryCharges, int orderPrice, String address, String time) {
        ItemsTotal = itemsTotal;
        OrderPrice = orderPrice;
        DeliveryCharges = deliveryCharges;
        OrderId = orderId;
        Address = address;
        Time = time;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
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
