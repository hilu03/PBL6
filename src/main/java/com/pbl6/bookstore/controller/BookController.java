package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.BookDetailDTO;
import com.pbl6.bookstore.response.APIResponse;
import com.pbl6.bookstore.response.MessageResponse;
import com.pbl6.bookstore.service.book.BookServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestController
public class BookController {

    private BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<APIResponse> getBookPerPage(@RequestParam(required = false) String page) {
        try {
            if (page == null) {
                page = "0";
            }

            Pageable pageWithFortyBooks = PageRequest.of(Integer.parseInt(page), 40);

            return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookService.getBookPerPage(pageWithFortyBooks)));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(MessageResponse.INVALID_PAGE_NUMBER, null));
        }
    }

    @GetMapping("/books/{bookID}")
    public ResponseEntity<APIResponse> findBookByID(@PathVariable String bookID) {
        BookDetailDTO bookDetailDTO = bookService.findBookById(bookID);
        if (bookDetailDTO != null) {
            return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookDetailDTO));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(MessageResponse.RESOURCE_NOT_FOUND, null));
    }

    @GetMapping("/books/category-name/{category}")
    public ResponseEntity<APIResponse> getBooksByCategoryName(@PathVariable String category,
                                                          @RequestParam(required = false) String page) {
        try {
            if (page == null) {
                page = "0";
            }
            Pageable pageable = PageRequest.of(Integer.parseInt(page), 40);
            return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookService.findBooksByCategoryName(category, pageable)));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(MessageResponse.INVALID_PAGE_NUMBER, null));
        }
    }

    @GetMapping("/books/category/{id}")
    public ResponseEntity<APIResponse> getBooksByCategoryID(@PathVariable int id,
                                                              @RequestParam(required = false) String page) {
        try {
            if (page == null) {
                page = "0";
            }
            Pageable pageable = PageRequest.of(Integer.parseInt(page), 40);
            return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookService.findBooksByCategoryID(id, pageable)));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(MessageResponse.INVALID_PAGE_NUMBER, null));
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new APIResponse(ex.getMessage(), null));
    }

    @GetMapping("/books/target-name/{target}")
    public ResponseEntity<APIResponse> getBooksByTargetName(@PathVariable String target,
                                                          @RequestParam(required = false) String page) {

        try {
            if (page == null) {
                page = "0";
            }
            Pageable pageable = PageRequest.of(Integer.parseInt(page), 40);
            return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookService.findBooksByTargetName(target, pageable)));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(MessageResponse.INVALID_PAGE_NUMBER, null));
        }
    }

    @GetMapping("/books/target/{id}")
    public ResponseEntity<APIResponse> getBooksByTargetID(@PathVariable String id,
                                                            @RequestParam(required = false) String page) {

        try {
            if (page == null) {
                page = "0";
            }
            Pageable pageable = PageRequest.of(Integer.parseInt(page), 40);
            return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookService.findBooksByTargetID(id, pageable)));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(MessageResponse.INVALID_PAGE_NUMBER, null));
        }

    }

    @GetMapping("books/hot-books")
    public ResponseEntity<APIResponse> getHotBook() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookService.getHotBooks()));
    }

    @GetMapping("books/sale-books")
    public ResponseEntity<APIResponse> getBestSellerBook() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, bookService.getSaleBooks()));
    }

}
