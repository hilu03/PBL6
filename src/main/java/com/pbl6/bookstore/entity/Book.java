package com.pbl6.bookstore.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Integer pages;

    @Column(name = "BookCover")
    private String cover;

    @Column(name = "Dimension")
    private String dimension;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                            CascadeType.MERGE, CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "bookauthor",
            joinColumns = @JoinColumn(name = "BookID"),
            inverseJoinColumns = @JoinColumn(name = "AuthorID"))
    private List<Author> authors;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "booktarget",
            joinColumns = @JoinColumn(name = "BookID"),
            inverseJoinColumns = @JoinColumn(name = "TargetID"))
    private List<Target> targets;

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

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }
}
