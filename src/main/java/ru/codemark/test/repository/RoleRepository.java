package ru.codemark.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.codemark.test.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
