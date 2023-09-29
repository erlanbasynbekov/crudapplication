package com.example.springbootdemo.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.example.springbootdemo.model.User;
import com.example.springbootdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testFindAll() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User(4L, "Ivan Ivanov"));
        mockUsers.add(new User(5L, "Sanya Sanyok"));

        String expectedViewName = "user-list";

        when(userService.findAll()).thenReturn(mockUsers);

        Model model = mock(Model.class);

        String actualViewName = userController.findAll(model);

        verify(model, times(1)).addAttribute("users", mockUsers);
        assertEquals(expectedViewName, actualViewName);

    }

    @Test
    public void testCreateUserForm() {
        String expectedViewName = "user-create";

        String actualViewName = userController.createUserForm(new User());

        assertEquals(expectedViewName, actualViewName);
    }

    @Test
    public void testCreateUser() {
        User user = new User(3L, "Ivan Ivanov");

        String actualViewName = userController.createUser(user);

        verify(userService, times(1)).saveUser(user);
        assertEquals("redirect:/users", actualViewName);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 3L;

        String actualViewName = userController.deleteUser(userId);

        verify(userService, times(1)).deleteById(userId);
        assertEquals("redirect:/users", actualViewName);
    }

    @Test
    public void testUpdateUserForm() {
        Long userId = 3L;
        User mockUser = new User(3L, "Ivan Ivanov");

        String expectedViewName = "user-update";

        when(userService.findById(userId)).thenReturn(mockUser);

        Model model = mock(Model.class);

        String actualViewName = userController.updateUserForm(userId, model);

        verify(model, times(1)).addAttribute("user", mockUser);
        assertEquals(expectedViewName, actualViewName);
    }

    @Test
    public void testUpdateUser() {
        User user = new User(3L, "Ivan Ivanov");

        String actualViewName = userController.updateUser(user);

        verify(userService, times(1)).saveUser(user);
        assertEquals("redirect:/users", actualViewName);
    }
}
