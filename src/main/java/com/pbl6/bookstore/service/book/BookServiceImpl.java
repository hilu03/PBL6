package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.dao.BookRepository;
import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.entity.Author;
import com.pbl6.bookstore.entity.Book;
import com.pbl6.bookstore.entity.Target;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    private ModelMapper modelMapper;

    public BookServiceImpl (BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<BookDTO> getBookPerPage(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookDTO> bookDTOList = books.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDTOList, pageable, books.getTotalElements());
    }

    public BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        bookDTO.setCategory(book.getCategory().getName());

        bookDTO.setAuthors(new ArrayList<>());
        for (Author author: book.getAuthors()) {
            bookDTO.addAuthor(author.getName());
        }

        bookDTO.setTargets(new ArrayList<>());
        for (Target target: book.getTargets()) {
            bookDTO.addTarget(target.getName());
        }

        return bookDTO;
    }
}
