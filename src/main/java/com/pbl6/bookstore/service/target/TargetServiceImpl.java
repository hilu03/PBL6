package com.pbl6.bookstore.service.target;

import com.pbl6.bookstore.dao.TargetRepository;
import com.pbl6.bookstore.entity.Target;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TargetServiceImpl implements TargetService{

    private TargetRepository targetRepository;

    public TargetServiceImpl(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    @Override
    public List<Target> findAll() {
        return targetRepository.findAll();
    }
}
