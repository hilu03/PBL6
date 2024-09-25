package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.Book;
import com.pbl6.bookstore.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TargetRepository extends JpaRepository<Target, String> {

    public Target findByName(String name);

    @Query("select t.books from Target t where t.id = :id")
    List<Book> findBooksByTargetID(@Param("id") String id);

    @Query("select t.books from Target t where t.name = :name")
    List<Book> findBooksByTargetName(@Param("name") String name);

}
