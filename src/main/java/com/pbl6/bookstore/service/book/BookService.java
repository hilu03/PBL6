package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookService {

    Page<BookDTO> getBookPerPage(Pageable pageable);

    BookDTO findBookById(String id);

    Page<BookDTO> findBooksByCategoryName(String categoryName, Pageable pageable);

    Page<BookDTO> findBooksByCategoryID(int id, Pageable pageable);

    Page<BookDTO> findBooksByTargetName(String targetName, Pageable pageable);

    Page<BookDTO> findBooksByTargetID(String id, Pageable pageable);

//    Page<BookDTO> findBookByAuthor(String author);

//    BookDTO addBook(Book book);

//    BookDTO updateBook(Book book);

//    void deleteBookById(String id);

}
