package com.pbl6.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "publisher")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Publisher {

    @Id
    @Column(name = "PublisherID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "PublisherName")
    String name;

    @OneToMany(mappedBy = "publisher",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JsonIgnore
    List<Book> books;
}
