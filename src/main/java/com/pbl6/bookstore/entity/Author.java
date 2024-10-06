package com.pbl6.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "author")
@Setter
@Getter
public class Author {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuthorID")
    private int id;

    @Column(name = "AuthorName")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "bookauthor",
            joinColumns = @JoinColumn(name = "AuthorID"),
            inverseJoinColumns = @JoinColumn(name = "BookID"))
    private List<Book> books;

    @Override
    public String toString() {
        return this.name;
    }
}
