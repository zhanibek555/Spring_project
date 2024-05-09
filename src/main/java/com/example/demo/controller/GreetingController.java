package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/greeting")
@RestController
public class GreetingController {

    private final UserService userService;

    @Autowired
    public GreetingController(UserService userService){
        this.userService = userService;
    }

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String greeting() {
        return "Welcome to the Spring Boot world!";
    }

    @PostMapping("/")
    public String personalizedGreeting(@RequestParam String name) {
        return "Hello " + name + "!";
    }
    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        logger.info("Added user: {}", newUser.toString());
        return newUser;
    }
    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/getEmployees")
//    public List<Employee> getEmployees(){
//
//    }

}
