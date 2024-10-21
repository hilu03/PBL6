package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.Author;
import com.pbl6.bookstore.entity.Book;
import com.pbl6.bookstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findAll(Pageable pageable);

    @Query("select b from Book b where b.category.id = :id")
    Page<Book> findAllByCategoryID(@Param("id") int id, Pageable pageable);

//    @Query("select b from Book b where b.category.name = :name")
//    List<Book> findBooksByCategoryName(@Param("name") String name);

    List<Book> findTop20ByOrderBySoldQuantityDesc();

    // 20 cuốn théo discount
    @Query(value = "SELECT * FROM book ORDER BY ((OriginalPrice - DiscountPrice) / OriginalPrice) DESC LIMIT 20",
            nativeQuery = true)
    List<Book> findTop20ByDiscountDesc();

    long countByCategory(Category category);

    List<Book> findByCategoryAndIdNot(Category category, String Id);

    @Query(value = "SELECT * FROM book WHERE MATCH (Title) AGAINST (?1)",
            nativeQuery = true)
    Page<Book> searchByTitle(String keyword, Pageable pageable);

}
