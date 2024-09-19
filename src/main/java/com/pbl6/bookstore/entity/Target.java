package com.pbl6.bookstore.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "target")
public class Target {

    @Id
    @Column(name = "TargetID")
    private String id;

    @Column(name = "TargetName")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "booktarget",
            joinColumns = @JoinColumn(name = "TargetID"),
            inverseJoinColumns = @JoinColumn(name = "BookID"))
    List<Book> books;

    public Target() {

    }

    public Target(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
