package com.nilscreation.yummyzone.Models;

import java.util.ArrayList;

public class OrderDetails {

    int ItemsTotal, OrderPrice, DeliveryCharges;
    String OrderId;
    String Address, Time;

    private ArrayList<FoodModel> products;

    public OrderDetails() {

    }

    public OrderDetails(String OrderId, int itemsTotal, int deliveryCharges, int orderPrice, String address, String time, ArrayList<FoodModel> products) {
        ItemsTotal = itemsTotal;
        OrderPrice = orderPrice;
        DeliveryCharges = deliveryCharges;
        this.OrderId = OrderId;
        Address = address;
        Time = time;
        this.products = products;
    }

    public ArrayList<FoodModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<FoodModel> products) {
        this.products = products;
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
