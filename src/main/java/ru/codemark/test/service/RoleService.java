package ru.codemark.test.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.codemark.test.model.Role;
import ru.codemark.test.repository.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private EntityManager entityManager;

    @Autowired
    public RoleService(RoleRepository roleRepository, EntityManager entityManager) {
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public Role update(Role role) {
        return roleRepository.save(role);
    }

    public void delete(Role role) {
        roleRepository.delete(role);
    }

    public List<Role> getAll() {
        entityManager.clear();
        return roleRepository.findAll();
    }

    public Role getById(Long id) {
        return roleRepository.findById(id).get();
    }

    public boolean existsById(Long id) {
        return roleRepository.existsById(id);
    }
}
