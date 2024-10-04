package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    int id;

    @Column(name = "FullName")
    String fullName;

    @Column(name = "PhoneNumber")
    String phoneNumber;

    @Column(name = "Password")
    String password;

    @Column(name = "Role")
    String role;

    @Column(name = "Email")
    String email;

    @Column(name = "Username")
    String username;

    @Column(name = "Address")
    String address;

    @OneToOne(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    Cart cart;

}
