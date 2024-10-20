package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.exception.AppException;
import com.pbl6.bookstore.exception.ErrorCode;
import com.pbl6.bookstore.mapper.BookMapper;
import com.pbl6.bookstore.repository.AuthorRepository;
import com.pbl6.bookstore.repository.BookRepository;
import com.pbl6.bookstore.repository.TargetRepository;
import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.dto.BookDetailDTO;
import com.pbl6.bookstore.entity.Book;
import lombok.AccessLevel;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;

    TargetRepository targetRepository;

    BookMapper bookMapper;

    AuthorRepository authorRepository;

    public BookDetailDTO convertToBookDetailDTO(Book book) {
        return bookMapper.toBookDetailDto(book);
    }

    public BookDTO convertToBookDTO(Book book) {

        return bookMapper.toBookDto(book);
    }

    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookDTO> bookDTOList = books.getContent().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDTOList, pageable, books.getTotalElements());
    }

    @Override
    public BookDetailDTO findBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);

        return book.map(this::convertToBookDetailDTO).orElse(null);

    }

    @Override
    public Page<BookDTO> findBooksByCategoryID(int id, Pageable pageable) {
        Page<Book> books = bookRepository.findAllByCategoryID(id, pageable);

        List<BookDTO> bookDTOList = books.getContent().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDTOList, pageable, books.getTotalElements());
    }

    @Override
    public Page<BookDTO> findBooksByTargetID(String id, Pageable pageable) {

        Page<Book> books = targetRepository.findBooksByTargetID(id, pageable);

        List<BookDTO> bookDTOList = books.getContent().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDTOList, pageable, books.getTotalElements());
    }

    @Override
    public List<BookDTO> getHotBooks() {
        return bookRepository.findTop20ByOrderBySoldQuantityDesc().stream().map(this::convertToBookDTO).toList();
    }

    @Override
    public List<BookDTO> getSaleBooks() {
        return bookRepository.findTop20ByDiscountDesc().stream().map(this::convertToBookDTO).toList();
    }

    @Override
    public List<BookDTO> getSameCategoryBooks(String bookID) {
        Book book = bookRepository.findById(bookID).orElseThrow(() -> new AppException(ErrorCode.BOOK_ID_NOT_FOUND));
        List<Book> books = bookRepository.findByCategoryAndIdNot(book.getCategory(), bookID);
        Collections.shuffle(books);
        return books.stream().limit(20).map(this::convertToBookDTO).toList();
    }

    @Override
    public Page<BookDTO> findBooksByAuthorID(int id, Pageable pageable) {
        Page<Book> books = authorRepository.findBooksByAuthorID(id, pageable);

        List<BookDTO> bookDTOList = books.getContent().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDTOList, pageable, books.getTotalElements());
    }


    //    @Override
//    public Page<BookDTO> findBooksByCategoryName(String categoryName, Pageable pageable) {
//        List<BookDTO> bookDTOList = bookRepository.findBooksByCategoryName(categoryName).stream().map(this::convertToBookDTO).toList();
//        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
//    }

    //    @Override
//    public Page<BookDTO> findBooksByTargetName(String targetName, Pageable pageable) {
//
//        List<BookDTO> bookDTOList = targetRepository.findBooksByTargetName(targetName).stream().map(this::convertToBookDTO).toList();
//        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
//    }

}
