package com.jaypal.ecommerce.model;

public class cart_model {
    int cart_id;
    int customer_id;
    int product_id;
    int shop_id;
    int quantity;
    int price;
    int status;

    public cart_model() {
    }

    public cart_model(int cart_id, int customer_id, int product_id, int shop_id, int quantity, int price, int status) {
        this.cart_id = cart_id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.shop_id = shop_id;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
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
}
