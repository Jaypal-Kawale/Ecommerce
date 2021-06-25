package com.jaypal.ecommerce.model;

public class shop_model {
    int shop_id;
    String shop_name;
    int category_id;
    String shop_img;

    public shop_model() {
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getShop_img() {
        return shop_img;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public shop_model(int shop_id, String shop_name, int category_id, String shop_img) {
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.category_id = category_id;
        this.shop_img = shop_img;
    }
}
