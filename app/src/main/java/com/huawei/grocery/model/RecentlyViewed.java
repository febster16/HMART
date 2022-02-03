package com.huawei.grocery.model;

public class RecentlyViewed {

    Product product;

    public RecentlyViewed(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
