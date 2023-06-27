package com.nilscreation.yummyzone.Models;

public class FoodModel {

    String Category, Title, Description, ImageUrl, Status;
    int Price, FinalPrice, DeliveryCharges, Qty;

    public FoodModel() {

    }

    public FoodModel(String mtitle, String mCategory, String mimageUrl, int mprice, int finalprice, int mdeliveryCharges, int qtyNumber) {
        this.Title = mtitle;
        this.Category = mCategory;
        this.ImageUrl = mimageUrl;
        this.Price = mprice;
        this.FinalPrice = finalprice;
        this.DeliveryCharges = mdeliveryCharges;
        this.Qty = qtyNumber;
    }

    public FoodModel(String Title, String ImageUrl, int Price, int DeliveryCharges, int Qty) {
        this.Title = Title;
        this.ImageUrl = ImageUrl;
        this.Price = Price;
        this.DeliveryCharges = DeliveryCharges;
        this.Qty = Qty;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getFinalPrice() {
        return FinalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        FinalPrice = finalPrice;
    }

    public int getDeliveryCharges() {
        return DeliveryCharges;
    }

    public void setDeliveryCharges(int deliveryCharges) {
        DeliveryCharges = deliveryCharges;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
