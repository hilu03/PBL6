package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookService {

    Page<BookDTO> getBookPerPage(Pageable pageable);

    BookDTO findBookById(String id);

//    Page<BookDTO> findBooksByCategoryName(String category);

//    Page<BookDTO> findBooksByTargetName(String target);

//    Page<BookDTO> findBookByAuthor(String author);

//    BookDTO addBook(Book book);

//    BookDTO updateBook(Book book);

//    void deleteBookById(String id);

}
