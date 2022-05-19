package ru.codemark.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.codemark.test.model.User;
import ru.codemark.test.model.dto.UserDtoForGetAll;
import ru.codemark.test.service.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDtoForGetAll> getAll() {
        return userService.getAll();
    }

    @GetMapping("{login}")
    public User getById(@PathVariable String login) {
        return userService.getById(login);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@Validated @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Validated @RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("{login}")
    public void delete(@PathVariable String login) {
        userService.delete(userService.getById(login));
    }
}
