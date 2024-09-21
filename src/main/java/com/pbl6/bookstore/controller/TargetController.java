package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.response.APIResponse;
import com.pbl6.bookstore.response.MessageResponse;
import com.pbl6.bookstore.service.target.TargetService;
import com.pbl6.bookstore.service.target.TargetServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TargetController {

    private TargetServiceImpl targetService;

    public TargetController(TargetServiceImpl targetService) {
        this.targetService = targetService;
    }

    @GetMapping("/targets")
    public ResponseEntity<APIResponse> findAll() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, targetService.findAll()));
    }
}
