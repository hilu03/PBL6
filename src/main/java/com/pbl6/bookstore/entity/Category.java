package com.pbl6.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    int id;

    @Column(name = "CategoryName")
    String name;

    @Column(name = "Slug")
    private String slug;

    @OneToMany(mappedBy = "category",
                cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                            CascadeType.MERGE, CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JsonIgnore
    List<Book> books;

    @Override
    public String toString() {
        return this.name;
    }

}
