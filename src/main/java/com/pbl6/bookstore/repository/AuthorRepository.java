package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.Author;
import com.pbl6.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("select b from Author a join a.books b where a.id = :id")
    Page<Book> findBooksByAuthorID(@Param("id") int id, Pageable pageable);

}
