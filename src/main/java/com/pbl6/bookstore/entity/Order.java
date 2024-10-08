package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Order")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @Column(name = "OrderID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "DateOrder")
    Date dateOrder;

    @Column(name = "PaymentLink")
    String paymentLink;

    @Column(name = "PaymentStatus")
    String paymentStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderStatusID")
    OrderStatus orderStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentMethodID")
    PaymentMethod paymentMethod;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "ShippingAddressID")
    ShippingAddress address;

    @OneToMany(mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    List<OrderItem> items;
}
