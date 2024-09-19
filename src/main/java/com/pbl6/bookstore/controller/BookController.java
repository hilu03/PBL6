package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.service.book.BookServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Page<BookDTO> getBookPerPage(@RequestParam(required = false) String page) {
        if (page == null) {
            page = "0";
        }

        Pageable pageWithFortyBooks = PageRequest.of(Integer.parseInt(page), 40);

        return bookService.getBookPerPage(pageWithFortyBooks);
    }

}
