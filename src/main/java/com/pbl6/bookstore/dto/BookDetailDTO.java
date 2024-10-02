package com.pbl6.bookstore.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDetailDTO {
    String id;

    String title;

    String publisher;

    Date datePublish;

    String description;

    BigDecimal originalPrice;

    BigDecimal discountedPrice;

    String imageLink;

    int soldQuantity;

    Integer availableQuantity;

    Integer pages;

    String cover;

    String dimension;

    String category;

    List<String> authors;

    List<String> targets;

    public void addTarget(String target) {
        if (targets == null) {
            targets = new ArrayList<>();
        }
        targets.add(target);
    }

    public void addAuthor(String author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }

}
