package com.jaypal.ecommerce.model;

import java.io.Serializable;

public class product_model  {
int product_id;
String product_name;
String product_description;
String product_img;
int product_price;
int subcategory_id;
int category_id;
int shop_id;

    public product_model(int product_id, String product_name, String product_description, String product_img, int product_price, int subcategory_id, int category_id, int shop_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_img = product_img;
        this.product_price = product_price;
        this.subcategory_id = subcategory_id;
        this.category_id = category_id;
        this.shop_id = shop_id;
    }

    public product_model() {
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }
}
