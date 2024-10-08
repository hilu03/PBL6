package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "OrderItem")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemID")
    int id;

    @Column(name = "Quantity")
    int quantity;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID")
    Order order;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "BookID")
    Book book;
}
