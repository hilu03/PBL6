package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "cart")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @Id
    @Column(name = "CartID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    User user;
}
