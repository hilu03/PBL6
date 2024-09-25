package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.dto.BookDetailDTO;
import com.pbl6.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    Page<BookDetailDTO> getBookPerPage(Pageable pageable);

    BookDetailDTO findBookById(String id);

    Page<BookDTO> findBooksByCategoryName(String categoryName, Pageable pageable);

    Page<BookDTO> findBooksByCategoryID(int id, Pageable pageable);

    Page<BookDTO> findBooksByTargetName(String targetName, Pageable pageable);

    Page<BookDTO> findBooksByTargetID(String id, Pageable pageable);

    List<BookDTO> getHotBooks();

    List<BookDTO> getSaleBooks();

//    Page<BookDTO> findBookByAuthor(String author);

//    Book addBook(BookDetailDTO book);

//    BookDTO updateBook(Book book);

//    void deleteBookById(String id);

}
