package com.pbl6.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public String toString() {
        return this.name;
    }
}
