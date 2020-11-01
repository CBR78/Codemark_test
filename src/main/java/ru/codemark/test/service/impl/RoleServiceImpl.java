package ru.codemark.test.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.codemark.test.model.Role;
import ru.codemark.test.repository.RoleRepository;
import ru.codemark.test.service.BaseService;

@Service
public class RoleServiceImpl implements BaseService<Role> {

    private RoleRepository roleRepository;
    private EntityManager entityManager;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, EntityManager entityManager) {
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
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
