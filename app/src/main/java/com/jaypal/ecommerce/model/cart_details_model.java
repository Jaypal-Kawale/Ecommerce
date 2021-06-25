package com.jaypal.ecommerce.model;

public class cart_details_model {
    int cart_id;
    int customer_id;
    int product_id;
    int shop_id;
    int quantity;
    int price;
    int status;
    String product_name;
    String product_description;
    String product_img;
    int product_price;

    public cart_details_model(int cart_id, int customer_id, int product_id, int shop_id, int quantity, int price, int status, String product_name, String product_description, String product_img, int product_price) {
        this.cart_id = cart_id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.shop_id = shop_id;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_img = product_img;
        this.product_price = product_price;
    }

    public cart_details_model() {
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
