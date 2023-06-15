package com.nilscreation.yummyzone.Models;

public class FoodModel {

    String Category, Title, Description, ImageUrl;
    int Price, DeliveryCharges, qty, orderId;

    public FoodModel() {

    }

    public FoodModel(int orderId, String title, String category, String imageUrl, int price, int deliveryCharges, int qty) {
        Category = category;
        Title = title;
        ImageUrl = imageUrl;
        Price = price;
        DeliveryCharges = deliveryCharges;
        this.qty = qty;
        this.orderId = orderId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getDeliveryCharges() {
        return DeliveryCharges;
    }

    public void setDeliveryCharges(int deliveryCharges) {
        DeliveryCharges = deliveryCharges;
    }

}
