package com.pbl6.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "shippingaddress",
        uniqueConstraints = {
        @UniqueConstraint(name = "UniqueAddress",
                columnNames = { "receiver", "phoneNumber", "address",
                "city", "district" , "ward"})
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingAddress {

    @Id
    @Column(name = "ShippingAddressID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "ReceiverName")
    String receiver;

    @Column(name = "PhoneNumber")
    String phoneNumber;

    @Column(name = "Address")
    String address;

    @Column(name = "City")
    String city;

    @Column(name = "District")
    String district;

    @Column(name = "Ward")
    String ward;

    @Column(name = "IsDefault")
    boolean isDefault;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    @JsonIgnore
    User user;

    @OneToMany(mappedBy = "address",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JsonIgnore
    List<Order> orders;
}
