package com.example.productsearchapp.pojo;

import java.util.ArrayList;

public class DetailItemProduct {
    private String pictureUrl1;
    private String pictureUrl2;
    private String pictureUrl3;
    private String title;
    private String price;
    private String shipping;
    private String subtitle;
    private ArrayList<String> itemSpecificsName;
    private ArrayList<ArrayList<String>> itemSpecificsValue;

    public DetailItemProduct(){}

    public DetailItemProduct(String pictureUrl1, String pictureUrl2, String pictureUrl3, String title, String price, String shipping, String subtitle, ArrayList<String> itemSpecificsName, ArrayList<ArrayList<String>> itemSpecificsValue) {
        this.pictureUrl1 = pictureUrl1;
        this.pictureUrl2 = pictureUrl2;
        this.pictureUrl3 = pictureUrl3;
        this.title = title;
        this.price = price;
        this.shipping = shipping;
        this.subtitle = subtitle;
        this.itemSpecificsName = itemSpecificsName;
        this.itemSpecificsValue = itemSpecificsValue;
    }

    public String getPictureUrl1() {
        return pictureUrl1;
    }
    public String getPictureUrl2() {
        return pictureUrl2;
    }
    public String getPictureUrl3() {
        return pictureUrl3;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getShipping() {
        return shipping;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ArrayList<String> getItemSpecificsName() {
        return itemSpecificsName;
    }

    public ArrayList<ArrayList<String>> getItemSpecificsValue() {
        return itemSpecificsValue;
    }

    public void setPictureUrl1(String pictureUrl1) {
        this.pictureUrl1 = pictureUrl1;
    }
    public void setPictureUrl2(String pictureUrl2) {
        this.pictureUrl2 = pictureUrl2;
    }
    public void setPictureUrl3(String pictureUrl3) {
        this.pictureUrl3 = pictureUrl3;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setItemSpecificsName(ArrayList<String> itemSpecificsName) {
        this.itemSpecificsName = itemSpecificsName;
    }

    public void setItemSpecificsValue(ArrayList<ArrayList<String>> itemSpecificsValue) {
        this.itemSpecificsValue = itemSpecificsValue;
    }
}
