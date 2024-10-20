package com.pbl6.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuthorID")
    int id;

    @Column(name = "AuthorName")
    String name;

    @Column(name = "Slug")
    String slug;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "bookauthor",
            joinColumns = @JoinColumn(name = "AuthorID"),
            inverseJoinColumns = @JoinColumn(name = "BookID"))
    @JsonIgnore
    List<Book> books;

    @Override
    public String toString() {
        return this.name;
    }
}
