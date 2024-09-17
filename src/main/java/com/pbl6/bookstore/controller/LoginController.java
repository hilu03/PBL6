package com.pbl6.bookstore.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pbl6.bookstore.service.LoginServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String checkLogin(@RequestBody ObjectNode objectNode) {
        return loginService.checkLogin(objectNode.get("username").asText(), objectNode.get("password").asText());
    }
}
