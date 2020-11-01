package ru.codemark.test.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import ru.codemark.test.validation.ContainDigit;
import ru.codemark.test.validation.ContrainUpperCaseChar;

@Data
@Entity
@Table(name = "USER")
public class User {

    @Id
    @NotNull(message = "Request must include a User login.")
    private String login;

    @NotNull(message = "Request must include a User password.")
    @ContainDigit(message = "Password must contain a digit", typeObject = "User")
    @ContrainUpperCaseChar(message = "Password must contain uppercase characters", typeObject = "User")
    private String password;

    @NotNull(message = "Request must include a User name.")
    private String name;

    @ManyToMany
    private List<Role> roles;
}
