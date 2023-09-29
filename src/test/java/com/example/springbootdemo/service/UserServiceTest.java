package com.example.springbootdemo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.example.springbootdemo.model.User;
import com.example.springbootdemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindById() {
        Long userId = 3L;
        User mockUser = new User(3L, "Ivan Ivanov");

        when(userRepository.getOne(userId)).thenReturn(mockUser);

        User foundUser = userService.findById(userId);

        assertEquals(mockUser, foundUser);
    }

    @Test
    public void testFindAll() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User(4L, "Ivan Ivanov"));
        mockUsers.add(new User(5L, "Alex Alexey"));

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> foundUsers = userService.findAll();

        assertEquals(mockUsers, foundUsers);
    }

    @Test
    public void testSaveUser() {
        User userToSave = new User(4L, "Ivan Ivanov");
        User savedUser = new User(5L, "Alex Alexey");

        when(userRepository.save(userToSave)).thenReturn(savedUser);

        User returnedUser = userService.saveUser(userToSave);

        assertEquals(savedUser, returnedUser);
    }

    @Test
    public void testDeleteById() {
        Long userId = 3L;

        userService.deleteById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
