package com.pbl6.bookstore.dto;

import com.pbl6.bookstore.entity.Author;
import com.pbl6.bookstore.entity.Category;
import com.pbl6.bookstore.entity.Target;
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

    String slug;

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

    List<Author> authors;

    List<Target> targets;

}
