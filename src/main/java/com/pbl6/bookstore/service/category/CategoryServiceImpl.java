package com.pbl6.bookstore.service.category;

import com.pbl6.bookstore.dto.response.CategoryDTO;
import com.pbl6.bookstore.repository.BookRepository;
import com.pbl6.bookstore.repository.CategoryRepository;
import com.pbl6.bookstore.entity.Category;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    BookRepository bookRepository;

    @Override
    public List<CategoryDTO> findAll() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        List<Category> categories = categoryRepository.findAllByOrderByNameAsc();

        for (Category category: categories) {
            CategoryDTO categoryDTO = CategoryDTO.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .quantity(bookRepository.countByCategory(category))
                    .build();
            categoryDTOList.add(categoryDTO);
        }

        return categoryDTOList;
    }


}
