package com.pbl6.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
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
    @JsonIgnore
    List<Book> books;

}
