package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.dao.BookRepository;
import com.pbl6.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    public BookServiceImpl (BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
