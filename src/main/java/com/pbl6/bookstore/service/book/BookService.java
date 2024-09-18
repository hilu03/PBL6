package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Page<Book> findAll(Pageable pageable);

}
