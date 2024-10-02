package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "PublisherID")
    private Publisher publisher;

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


}
