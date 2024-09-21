package com.pbl6.bookstore.dao;

import com.pbl6.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findAll(Pageable pageable);

    @Query("select b from Book b where b.category.id = :id")
    List<Book> findBooksByCategoryID(@Param("id") int id);

    @Query("select b from Book b where b.category.name = :name")
    List<Book> findBooksByCategoryName(@Param("name") String name);

}
