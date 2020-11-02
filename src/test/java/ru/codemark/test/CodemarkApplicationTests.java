package ru.codemark.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ru.codemark.test.model.Role;
import ru.codemark.test.model.User;

@SpringBootTest(classes = CodemarkApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class CodemarkApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void testAddRoles() {
        String urlRole = "http://localhost:" + port + "/role";

        // Add Role "user"
        Role requestObject = new Role(null, "user", null);
        ResponseEntity<Role> responseEntity = restTemplate.postForEntity(urlRole, requestObject,
                Role.class);
        assertEquals(new Role(1L, "user", null), responseEntity.getBody());
        assertEquals(201, responseEntity.getStatusCodeValue());

        // Add Role "admin"
        requestObject = new Role(null, "admin", null);
        responseEntity = restTemplate.postForEntity(urlRole, requestObject, Role.class);

        assertEquals(new Role(2L, "admin", null), responseEntity.getBody());
        assertEquals(201, responseEntity.getStatusCodeValue());

        // Add Role "analyst"
        requestObject = new Role(null, "analyst", null);
        responseEntity = restTemplate.postForEntity(urlRole, requestObject, Role.class);

        assertEquals(new Role(3L, "analyst", null), responseEntity.getBody());
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    void testAddUsers() {
        String urlUser = "http://localhost:" + port + "/user";

        // Add User "John Smith"
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, null, null));
        User requestObject = new User("john", "sdH4k", "John Smith", roles);
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(urlUser, requestObject,
                User.class);

        List<Role> targetRoles = new ArrayList<>();
        targetRoles.add(new Role(1L, "user", new ArrayList<User>()));
        assertEquals(new User("john", "sdH4k", "John Smith", targetRoles), responseEntity.getBody());
        assertEquals(201, responseEntity.getStatusCodeValue());

        // Add User "Maria Smith"
        roles.clear();
        roles.add(new Role(1L, null, null));
        roles.add(new Role(2L, null, null));
        requestObject = new User("maria", "sdF5l", "Maria Smith", roles);
        responseEntity = restTemplate.postForEntity(urlUser, requestObject, User.class);

        targetRoles.clear();
        targetRoles.add(new Role(1L, "user", new ArrayList<User>()));
        targetRoles.add(new Role(2L, "admin", new ArrayList<User>()));
        assertEquals(new User("maria", "sdF5l", "Maria Smith", targetRoles), responseEntity.getBody());
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(3)
    void testPutUser() {
        String urlUser = "http://localhost:" + port + "/user";

        // Put User "john"
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(2L, null, null));
        roles.add(new Role(3L, null, null));
        User requestObject = new User("john", "sdH4k + test", "John Smith + test", roles);
        HttpEntity<User> requestEntity = new HttpEntity<>(requestObject);
        ResponseEntity<User> responseEntity = restTemplate.exchange(urlUser, HttpMethod.PUT,
                requestEntity, User.class);

        List<Role> targetRoles = new ArrayList<>();
        targetRoles.add(new Role(2L, "admin", new ArrayList<User>()));
        targetRoles.add(new Role(3L, "analyst", new ArrayList<User>()));
        User targetUser = new User("john", "sdH4k + test", "John Smith + test", targetRoles);
        assertEquals(targetUser, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
