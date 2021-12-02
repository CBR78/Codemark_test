package ru.codemark.test;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import ru.codemark.test.model.Role;
import ru.codemark.test.model.User;
import ru.codemark.test.model.dto.UserDtoForGetAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CodemarkApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class CodemarkApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private final List<User> emptyUserList = Collections.emptyList();

    private final Role targetRoleForTestPut = new Role(1L, "forTestPut", emptyUserList);
    private final Role targetRoleUser = new Role(2L, "user", emptyUserList);
    private final Role targetRoleAdmin = new Role(3L, "admin", emptyUserList);
    private final Role targetRoleAnalyst = new Role(4L, "analyst", emptyUserList);

    private String getUrlRole() {
        return "http://localhost:" + port + "/role";
    }

    private String getUrlUser() {
        return "http://localhost:" + port + "/user";
    }

    // RoleController integration tests

    @Test
    @Order(1)
    void testAddRoles() {
        // Add Role "forTestPut"
        ResponseEntity<Role> responseEntity = restTemplate.postForEntity(getUrlRole(),
                new Role(null, "forTestPut", null), Role.class);

        assertEquals(new Role(1L, "forTestPut", null), responseEntity.getBody());
        assertEquals(201, responseEntity.getStatusCodeValue());

        // Adding Roles for the next tests
        restTemplate.postForEntity(getUrlRole(), new Role(null, "user", null), Role.class);
        restTemplate.postForEntity(getUrlRole(), new Role(null, "admin", null), Role.class);
        restTemplate.postForEntity(getUrlRole(), new Role(null, "analyst", null), Role.class);
    }

    @Test
    @Order(2)
    void testGetAllRoles() {
        ResponseEntity<Role[]> responseEntity = restTemplate.getForEntity(getUrlRole(), Role[].class);

        List<Role> targetRoleList = new ArrayList<>();
        targetRoleList.add(targetRoleForTestPut);
        targetRoleList.add(targetRoleUser);
        targetRoleList.add(targetRoleAdmin);
        targetRoleList.add(targetRoleAnalyst);

        assertEquals(targetRoleList, Arrays.asList(responseEntity.getBody()));
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(3)
    void testGetRoleById() {
        String urlRoleAndId = getUrlRole() + "/2";
        ResponseEntity<Role> responseEntity = restTemplate.getForEntity(urlRoleAndId, Role.class);

        assertEquals(targetRoleUser, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(4)
    void testPutRole() {
        // Put Role "forTestPut". Rename to "forTestPut+++"
        Role requestObject = new Role(1L, "forTestPut+++", null);
        ResponseEntity<Role> responseEntity = restTemplate.exchange(getUrlRole(), HttpMethod.PUT,
                new HttpEntity<>(requestObject), Role.class);

        Role targetRole = requestObject;

        assertEquals(targetRole, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(5)
    void testDeleteRole() {
        String urlRoleAndId = getUrlRole() + "/1";
        Role role = restTemplate.getForObject(urlRoleAndId, Role.class);

        assertNotNull(role);

        restTemplate.delete(urlRoleAndId);
        try {
            role = restTemplate.getForObject(urlRoleAndId, Role.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    // UserController integration tests

    @Test
    @Order(6)
    void testAddUsers() {
        // Add User "John Smith" - Role: user
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(2L, null, null));
        User requestObject = new User("john", "sdH4k", "John Smith", roles);
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(getUrlUser(), requestObject,
                User.class);

        roles.clear();
        roles.add(targetRoleUser);
        User targetUser = new User("john", "sdH4k", "John Smith", roles);

        assertEquals(targetUser, responseEntity.getBody());
        assertEquals(201, responseEntity.getStatusCodeValue());

        // Add User for the next tests. "Maria Smith" - Roles: user + admin
        roles.clear();
        roles.add(new Role(2L, null, null));
        roles.add(new Role(3L, null, null));
        requestObject = new User("maria", "sdF5l", "Maria Smith", roles);
        restTemplate.postForEntity(getUrlUser(), requestObject, User.class);
    }

    @Test
    @Order(7)
    void testGetAllUsers() {
        ResponseEntity<UserDtoForGetAll[]> responseEntity = restTemplate.getForEntity(getUrlUser(),
                UserDtoForGetAll[].class);

        List<UserDtoForGetAll> targetUserList = new ArrayList<>();
        targetUserList.add(new UserDtoForGetAll("john", "sdH4k", "John Smith"));
        targetUserList.add(new UserDtoForGetAll("maria", "sdF5l", "Maria Smith"));

        assertEquals(targetUserList, Arrays.asList(responseEntity.getBody()));
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(8)
    void testGetUserByLogin() {
        String urlUserAndLogin = getUrlUser() + "/maria";
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(urlUserAndLogin, User.class);

        List<Role> roles = new ArrayList<>();
        roles.add(targetRoleUser);
        roles.add(targetRoleAdmin);
        User targetUser = new User("maria", "sdF5l", "Maria Smith", roles);

        assertEquals(targetUser, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(9)
    void testPutUser() {
        // Put User "john". Remove role "user", add roles "admin" and "analyst"
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(3L, null, null));
        roles.add(new Role(4L, null, null));
        User requestObject = new User("john", "sdH4k + test", "John Smith + test", roles);
        ResponseEntity<User> responseEntity = restTemplate.exchange(getUrlUser(), HttpMethod.PUT,
                new HttpEntity<>(requestObject), User.class);

        List<Role> targetRoles = new ArrayList<>();
        targetRoles.add(targetRoleAdmin);
        targetRoles.add(targetRoleAnalyst);
        User targetUser = new User("john", "sdH4k + test", "John Smith + test", targetRoles);

        assertEquals(targetUser, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(10)
    void testDeleteUser() {
        String urlUserAndLogin = getUrlUser() + "/maria";
        User user = restTemplate.getForObject(urlUserAndLogin, User.class);

        assertNotNull(user);

        restTemplate.delete(urlUserAndLogin);
        try {
            user = restTemplate.getForObject(urlUserAndLogin, User.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    @Order(11)
    void testAddIncorrectUsers() {
        User userHasNoLogin = new User(null, "sdH4k", "John Smith", null);
        User userHasNoPassword = new User("john", null, "John Smith", null);
        User userHasNoName = new User("john", "sdH4k", null, null);
        User passwordHasNoDigit = new User("john", "sdHk", "John Smith", null);
        User passwordHasNoUpperCaseChar = new User("john", "sd4k", "John Smith", null);

        User userHasNoParameters = new User();
        ResponseEntity<String> responseEntity;

        try {
            responseEntity = restTemplate.postForEntity(getUrlUser(), userHasNoParameters, String.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }
}
