package ru.codemark.test.model;

import ru.codemark.test.validation.ContainDigit;
import ru.codemark.test.validation.ContrainUpperCaseChar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

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

    public User() {
    }

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public User(String login, String password, String name, List<Role> roles) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
