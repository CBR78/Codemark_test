package ru.codemark.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.codemark.test.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
