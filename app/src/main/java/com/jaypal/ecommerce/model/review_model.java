package com.jaypal.ecommerce.model;

import java.sql.Timestamp;

public class review_model {
    String rating;
    String review;
    String timestamp;
    String firstname;
    String lastname;

    public review_model(String rating, String review, String timestamp, String firstname, String lastname) {
        this.rating = rating;
        this.review = review;
        this.timestamp = timestamp;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public review_model() {
    }


    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
