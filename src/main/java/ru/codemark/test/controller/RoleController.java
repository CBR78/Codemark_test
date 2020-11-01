package ru.codemark.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.codemark.test.model.Role;
import ru.codemark.test.service.impl.RoleServiceImpl;

@RestController
@RequestMapping("role")
public class RoleController {
    private static final String CUSTOM_HEADER_NAME = "X-Query-Result";
    private HttpHeaders headers = new HttpHeaders();
    private RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAll() {
        List<Role> roles = roleService.getAll();
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "All objects Role found. Number of objects " + roles.size());
        return new ResponseEntity<>(roles, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> add(@RequestBody Role role) {
        Role createdRole = roleService.create(role);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Created Role object with id " + createdRole.getId());
        return new ResponseEntity<>(createdRole, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Role> update(@RequestBody Role role) {
        Role updatedRole = roleService.update(role);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Updated Role object with id " + updatedRole.getId());
        return new ResponseEntity<>(updatedRole, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Role> delete(@PathVariable Long id) {
        roleService.delete(roleService.getById(id));
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Deleted Role object with id " + id);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
