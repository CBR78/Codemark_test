package ru.codemark.test.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoForGetAll {
    private String login;
    private String password;
    private String name;
}
