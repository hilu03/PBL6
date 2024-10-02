package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.repository.BookRepository;
import com.pbl6.bookstore.repository.TargetRepository;
import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.dto.BookDetailDTO;
import com.pbl6.bookstore.entity.Author;
import com.pbl6.bookstore.entity.Book;
import com.pbl6.bookstore.entity.Target;
import lombok.AccessLevel;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;

    TargetRepository targetRepository;

    ModelMapper modelMapper;


    public BookDetailDTO convertToBookDetailDTO(Book book) {

        BookDetailDTO bookDetailDTO = modelMapper.map(book, BookDetailDTO.class);

        bookDetailDTO.setCategory(book.getCategory().getName());
        bookDetailDTO.setPublisher(book.getPublisher().getName());

        for (Author author : book.getAuthors()) {
            bookDetailDTO.addAuthor(author.getName());
        }

        for (Target target: book.getTargets()) {
            bookDetailDTO.addTarget(target.getName());
        }

        return bookDetailDTO;
    }

    public BookDTO convertToBookDTO(Book book) {

        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public Page<BookDetailDTO> getBookPerPage(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookDetailDTO> bookDetailDTOList = books.getContent().stream()
                .map(this::convertToBookDetailDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDetailDTOList, pageable, books.getTotalElements());
    }

    @Override
    public BookDetailDTO findBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);

        return book.map(this::convertToBookDetailDTO).orElse(null);

    }

    @Override
    public Page<BookDTO> findBooksByCategoryID(int id, Pageable pageable) {
        Page<Book> books = bookRepository.findAllByCategoryID(id, pageable);

        List<BookDTO> bookDetailDTOList = books.getContent().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDetailDTOList, pageable, books.getTotalElements());
    }

    @Override
    public Page<BookDTO> findBooksByTargetID(String id, Pageable pageable) {

        Page<Book> books = targetRepository.findBooksByTargetID(id, pageable);

        List<BookDTO> bookDetailDTOList = books.getContent().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDetailDTOList, pageable, books.getTotalElements());
    }

    @Override
    public List<BookDTO> getHotBooks() {
        return bookRepository.findTop20ByOrderBySoldQuantityDesc().stream().map(this::convertToBookDTO).toList();
    }

    @Override
    public List<BookDTO> getSaleBooks() {
        return bookRepository.findTop20ByDiscountDesc().stream().map(this::convertToBookDTO).toList();
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
