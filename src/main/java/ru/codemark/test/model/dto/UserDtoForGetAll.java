package ru.codemark.test.model.dto;

import java.util.Objects;

public class UserDtoForGetAll {
    private String login;
    private String password;
    private String name;

    public UserDtoForGetAll(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoForGetAll that = (UserDtoForGetAll) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name);
    }

    @Override
    public String toString() {
        return "UserDtoForGetAll{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
