package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.response.APIResponse;
import com.pbl6.bookstore.response.MessageResponse;
import com.pbl6.bookstore.service.book.BookServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<APIResponse> getBookPerPage(@RequestParam(required = false) String page) {
        if (page == null) {
            page = "0";
        }

        Pageable pageWithFortyBooks = PageRequest.of(Integer.parseInt(page), 40);

        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookService.getBookPerPage(pageWithFortyBooks)));
    }

    @GetMapping("/books/{bookID}")
    public ResponseEntity<APIResponse> findBookByID(@PathVariable String bookID) {
        BookDTO bookDTO = bookService.findBookById(bookID);
        if (bookDTO != null) {
            return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookDTO));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(MessageResponse.RESOURCE_NOT_FOUND, null));
    }

}
