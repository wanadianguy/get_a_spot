package com.universityparking.backend.controller;

import com.universityparking.backend.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public record UserController(
        UserService userService
) {
}
