package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("authentication: {}", authentication);
        User currentUser = (User) authentication.getPrincipal();
        logger.info("currentUser: {}", currentUser);
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = (List<User>) userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
}
