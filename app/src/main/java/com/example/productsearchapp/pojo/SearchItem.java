package com.example.productsearchapp.pojo;

public class SearchItem {
    private String itemId;
    private String galleryUrl;
    private String title;
    private double price;
    private double shippingCost;
    private String zipCode;
    private String condition;
    private String viewItemURL;

    public SearchItem(String itemId, String galleryUrl, String title, double price, double shippingCost, String zipCode, String condition, String viewItemURL) {
        this.itemId = itemId;
        this.galleryUrl = galleryUrl;
        this.title = title;
        this.price = price;
        this.shippingCost = shippingCost;
        this.zipCode = zipCode;
        this.condition = condition;
        this.viewItemURL = viewItemURL;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getGalleryUrl() {
        return galleryUrl;
    }

    public double getPrice() {
        return price;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCondition() {
        return condition;
    }

    public String getViewItemURL() {
        return viewItemURL;
    }
}
