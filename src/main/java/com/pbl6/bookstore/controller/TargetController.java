package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.service.target.TargetServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TargetController {

    TargetServiceImpl targetService;

    @GetMapping("/targets")
    public ResponseEntity<APIResponse> findAll() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, targetService.findAll()));
    }
}
