package com.pbl6.bookstore.service.book;

import com.pbl6.bookstore.dao.BookRepository;
import com.pbl6.bookstore.dao.CategoryRepository;
import com.pbl6.bookstore.dao.TargetRepository;
import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.entity.Author;
import com.pbl6.bookstore.entity.Book;
import com.pbl6.bookstore.entity.Category;
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

    private CategoryRepository categoryRepository;

    private TargetRepository targetRepository;

    private ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository,
                           TargetRepository targetRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.targetRepository = targetRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public Page<BookDTO> getBookPerPage(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookDTO> bookDTOList = books.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDTOList, pageable, books.getTotalElements());
    }

    @Override
    public BookDTO findBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) return convertToDTO(book.get());

        return null;
    }

    @Override
    public Page<BookDTO> findBooksByCategoryName(String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) return null;

        List<BookDTO> bookDTOList = category.getBooks().stream().map(this::convertToDTO).toList();

        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
    }

    @Override
    public Page<BookDTO> findBooksByCategoryID(int id, Pageable pageable) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {

            List<BookDTO> bookDTOList = category.get().getBooks().stream().map(this::convertToDTO).toList();

            return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
        }

        return null;
    }

    @Override
    public Page<BookDTO> findBooksByTargetName(String targetName, Pageable pageable) {

        Target target = targetRepository.findByName(targetName);
        if (target == null) return null;

        List<BookDTO> bookDTOList = target.getBooks().stream().map(this::convertToDTO).toList();

        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
    }

    @Override
    public Page<BookDTO> findBooksByTargetID(String id, Pageable pageable) {
        Optional<Target> target = targetRepository.findById(id);

        if (target.isEmpty()) return null;

        List<BookDTO> bookDTOList = target.get().getBooks().stream().map(this::convertToDTO).toList();

        return new PageImpl<>(bookDTOList, pageable, bookDTOList.size());
    }

}
