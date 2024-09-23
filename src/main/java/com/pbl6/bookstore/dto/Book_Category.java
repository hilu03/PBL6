package com.pbl6.bookstore.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Book_Category {
    private String id;
    private String title;
    private String imageLink;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private String category;

    public Book_Category(String id, String title, String imageLink, BigDecimal discountedPrice, BigDecimal originalPrice, String category) {
        this.id = id;
        this.title = title;
        this.imageLink = imageLink;
        this.discountedPrice = discountedPrice;
        this.originalPrice = originalPrice;
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public String getCategory() {
        return category;
    }
}
