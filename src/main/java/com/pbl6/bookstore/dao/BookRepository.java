package com.pbl6.bookstore.dao;

import com.pbl6.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findAll(Pageable pageable);
}
