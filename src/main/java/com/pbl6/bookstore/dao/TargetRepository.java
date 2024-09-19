package com.pbl6.bookstore.dao;

import com.pbl6.bookstore.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, String> {

    public Target findByName(String name);

}
