package ru.codemark.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.codemark.test.model.User;
import ru.codemark.test.model.dto.UserDtoForGetAll;
import ru.codemark.test.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
    private static final String CUSTOM_HEADER_NAME = "X-Query-Result";
    private HttpHeaders headers = new HttpHeaders();
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDtoForGetAll>> getAll() {
        List<UserDtoForGetAll> users = userService.getAll();
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "All objects User found. Number of objects " + users.size());
        return new ResponseEntity<>(users, headers, HttpStatus.OK);
    }

    @GetMapping("{login}")
    public ResponseEntity<User> getById(@PathVariable String login) {
        User user = userService.getById(login);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "User by login " + user.getLogin() + " found.");
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> add(@Validated @RequestBody User user) {
        User createdUser = userService.create(user);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Created User object with id " + createdUser.getLogin());
        return new ResponseEntity<>(createdUser, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> update(@Validated @RequestBody User user) {
        User updatedUser = userService.update(user);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Updated User object with id " + updatedUser.getLogin());
        return new ResponseEntity<>(updatedUser, headers, HttpStatus.OK);
    }

    @DeleteMapping("{login}")
    public ResponseEntity<User> delete(@PathVariable String login) {
        userService.delete(userService.getById(login));
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Deleted User object with id " + login);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
