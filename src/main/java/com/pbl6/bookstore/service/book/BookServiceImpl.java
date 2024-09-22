package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.dao.BookRepository;
import com.pbl6.bookstore.dao.TargetRepository;
import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.dto.BookDetailDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    private TargetRepository targetRepository;

    private ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           TargetRepository targetRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.targetRepository = targetRepository;
        this.modelMapper = modelMapper;
    }

    public BookDetailDTO convertToBookDetailDTO(Book book) {
        BookDetailDTO bookDetailDTO = modelMapper.map(book, BookDetailDTO.class);
        bookDetailDTO.setCategory(book.getCategory().getName());

        bookDetailDTO.setAuthors(new ArrayList<>());
        for (Author author: book.getAuthors()) {
            bookDetailDTO.addAuthor(author.getName());
        }

        bookDetailDTO.setTargets(new ArrayList<>());
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
    public Page<BookDTO> findBooksByCategoryName(String categoryName, Pageable pageable) {
        List<BookDTO> bookDTOList = bookRepository.findBooksByCategoryName(categoryName).stream().map(this::convertToBookDTO).toList();
        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
    }

    @Override
    public Page<BookDTO> findBooksByCategoryID(int id, Pageable pageable) {
        List<BookDTO> bookDTOList = bookRepository.findBooksByCategoryID(id).stream().map(this::convertToBookDTO).toList();
        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
    }

    @Override
    public Page<BookDTO> findBooksByTargetName(String targetName, Pageable pageable) {

        List<BookDTO> bookDTOList = targetRepository.findBooksByTargetName(targetName).stream().map(this::convertToBookDTO).toList();
        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
    }

    @Override
    public Page<BookDTO> findBooksByTargetID(String id, Pageable pageable) {
        List<BookDTO> bookDTOList = targetRepository.findBooksByTargetID(id).stream().map(this::convertToBookDTO).toList();
        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
    }

    @Override
    public List<BookDTO> getHotBooks() {
        return bookRepository.findTop20ByOrderBySoldQuantityDesc().stream().map(this::convertToBookDTO).toList();
    }

    @Override
    public List<BookDTO> getSaleBooks() {
        return bookRepository.findTop20ByDiscountDesc().stream().map(this::convertToBookDTO).toList();
    }

}
