package com.huawei.grocery.model;

import java.io.Serializable;

public class Product implements Serializable {

    int id;
    String name;
    String description;
    int price;
    String unit;
    int category_id;
    int imageUrl;
    int bgImageUrl;

    public Product(int id, String name, String description, int price, String unit, int category_id, int imageUrl , int bgImageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.category_id = category_id;
        this.imageUrl = imageUrl;
        this.bgImageUrl = bgImageUrl;
    }

    public Product getProduct(){
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBgImageUrl() {
        return bgImageUrl;
    }

    public void setBgImageUrl(int bgImageUrl) {
        this.bgImageUrl = bgImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
