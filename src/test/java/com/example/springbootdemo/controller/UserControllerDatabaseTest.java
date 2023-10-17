package com.example.springbootdemo.controller;

import com.example.springbootdemo.model.User;
import com.example.springbootdemo.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@DirtiesContext
public class UserControllerDatabaseTest {


    @Autowired
    private UserRepository userRepository;

    //deleting and cleaning all past
    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }
    @Test
    public void testCRUD() {
        //creating a new user
        User user = new User();
        user.setFirstName("asem");
        user.setLastName("asel");
        userRepository.save(user);

        //testing reading
        List<User> savedUsers = userRepository.findByFirstName("asem");
        assertNotNull(savedUsers);
        assertEquals(1, savedUsers.size());
        User savedUser = savedUsers.get(0);
        assertEquals("asem", savedUser.getFirstName());
        assertEquals("asel", savedUser.getLastName());

        //updating info
        savedUser.setFirstName("UpdatedFirstName");
        savedUser.setLastName("UpdatedLastName");
        userRepository.save(savedUser);

        //testing updates
        List<User> updatedUsers = userRepository.findByFirstName("UpdatedFirstName");
        assertNotNull(updatedUsers);
        assertEquals(1, updatedUsers.size());
        User updatedUser = updatedUsers.get(0);
        assertEquals("UpdatedFirstName", updatedUser.getFirstName());
        assertEquals("UpdatedLastName", updatedUser.getLastName());

        //deleting user
        userRepository.deleteById(updatedUser.getId());

        //verify deleting
        List<User> deletedUsers = userRepository.findByFirstName("UpdatedFirstName");
        assertEquals(0, deletedUsers.size());
    }
}