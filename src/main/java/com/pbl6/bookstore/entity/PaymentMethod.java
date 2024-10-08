package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "PaymentMethod")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod {

    @Id
    @Column(name = "PaymentMethodID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "MethodName")
    String name;

    @OneToMany(mappedBy = "paymentMethod",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    List<Order> orders;
}
