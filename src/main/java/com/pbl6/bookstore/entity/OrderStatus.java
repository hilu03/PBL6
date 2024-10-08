package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "OrderStatus")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatus {

    @Id
    @Column(name = "OrderStatusID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "StatusName")
    String statusName;

    @OneToMany(mappedBy = "orderStatus",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    List<Order> orders;
}
