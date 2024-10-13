package com.pbl6.bookstore.mapper;

import com.pbl6.bookstore.dto.BookDTO;
import com.pbl6.bookstore.dto.BookDetailDTO;
import com.pbl6.bookstore.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "publisher.name", target = "publisher")
    @Mapping(source = "category.name", target = "category")
    BookDetailDTO toBookDetailDto(Book book);

    BookDTO toBookDto(Book book);
}
