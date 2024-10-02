package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.Book;
import com.pbl6.bookstore.entity.Target;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TargetRepository extends JpaRepository<Target, String> {

    @Query("select b from Target t join t.books b where t.id = :id")
    Page<Book> findBooksByTargetID(@Param("id") String id, Pageable pageable);

//    @Query("select t.books from Target t where t.name = :name")
//    List<Book> findBooksByTargetName(@Param("name") String name);

}
