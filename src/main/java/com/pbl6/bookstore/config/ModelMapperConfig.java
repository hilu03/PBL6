package com.pbl6.bookstore.config;

import com.pbl6.bookstore.dto.BookDetailDTO;
import com.pbl6.bookstore.entity.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Book, BookDetailDTO>() {
            @Override
            protected void configure() {
                skip(destination.getCategory());
                skip(destination.getPublisher());
                skip(destination.getAuthors());
                skip(destination.getTargets());
            }
        });
        return new ModelMapper();
    }
}
