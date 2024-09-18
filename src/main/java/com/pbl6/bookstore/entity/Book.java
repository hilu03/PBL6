package com.pbl6.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "BookID")
    private String id;

    @Column(name = "Title")
    private String title;

    @Column(name = "DatePublish")
    private Date datePublish;

    @Column(name = "Description")
    private String description;

    @Column(name = "OriginalPrice")
    private BigDecimal originalPrice;

    @Column(name = "DiscountPrice")
    private BigDecimal discountedPrice;

    @Column(name = "Image")
    private String imageLink;

    @Column(name = "SoldQuantity")
    private int soldQuantity;

    @Column(name = "AvailableQuantity")
    private Integer availableQuantity;

    @Column(name = "Pages")
    private int pages;

    @Column(name = "BookCover")
    private String cover;

    @Column(name = "Dimension")
    private String dimension;

    public Book() {

    }

    public Book(String id, String title, Date datePublish, String description, BigDecimal originalPrice,
                BigDecimal discountedPrice, String imageLink, int soldQuantity, int availableQuantity,
                int pages, String cover, String dimension) {
        this.id = id;
        this.title = title;
        this.datePublish = datePublish;
        this.description = description;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.imageLink = imageLink;
        this.soldQuantity = soldQuantity;
        this.availableQuantity = availableQuantity;
        this.pages = pages;
        this.cover = cover;
        this.dimension = dimension;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(Date datePublish) {
        this.datePublish = datePublish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", datePublish=" + datePublish +
                ", description='" + description + '\'' +
                ", originalPrice=" + originalPrice +
                ", discountedPrice=" + discountedPrice +
                ", imageLink='" + imageLink + '\'' +
                ", soldQuantity=" + soldQuantity +
                ", availableQuantity=" + availableQuantity +
                ", pages=" + pages +
                ", cover='" + cover + '\'' +
                ", dimension='" + dimension + '\'' +
                '}';
    }
}
