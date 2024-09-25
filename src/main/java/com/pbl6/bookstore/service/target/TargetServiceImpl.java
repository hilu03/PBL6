package com.pbl6.bookstore.service.target;

import com.pbl6.bookstore.repository.TargetRepository;
import com.pbl6.bookstore.entity.Target;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TargetServiceImpl implements TargetService{

    TargetRepository targetRepository;

    @Override
    public List<Target> findAll() {
        return targetRepository.findAll();
    }
}
