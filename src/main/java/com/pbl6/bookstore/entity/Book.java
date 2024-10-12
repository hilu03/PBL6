package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @Column(name = "BookID")
    String id;

    @Column(name = "Title")
    String title;

    @Column(name = "DatePublish")
    Date datePublish;

    @Column(name = "Description")
    String description;

    @Column(name = "OriginalPrice")
    BigDecimal originalPrice;

    @Column(name = "DiscountPrice")
    BigDecimal discountedPrice;

    @Column(name = "Image")
    String imageLink;

    @Column(name = "SoldQuantity")
    int soldQuantity;

    @Column(name = "AvailableQuantity")
    Integer availableQuantity;

    @Column(name = "Pages")
    Integer pages;

    @Column(name = "BookCover")
    String cover;

    @Column(name = "Dimension")
    String dimension;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                            CascadeType.MERGE, CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    Category category;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "PublisherID")
    Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "BookAuthor",
            joinColumns = @JoinColumn(name = "BookID"),
            inverseJoinColumns = @JoinColumn(name = "AuthorID"))
    List<Author> authors;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "BookTarget",
            joinColumns = @JoinColumn(name = "BookID"),
            inverseJoinColumns = @JoinColumn(name = "TargetID"))
    List<Target> targets;

    @OneToMany(mappedBy = "book",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    List<CartItem> cartItems;
}
