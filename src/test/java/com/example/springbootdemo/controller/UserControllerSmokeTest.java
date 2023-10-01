package com.example.springbootdemo.controller;

import com.example.springbootdemo.model.User;
import com.example.springbootdemo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
public class UserControllerSmokeTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @Before
    public void setUp() {
        userController = new UserController(userService);
    }

    @Test
    public void findAll() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User());
        mockUsers.add(new User());

        when(userService.findAll()).thenReturn(mockUsers);

        Model model = new ConcurrentModel();//здесь м

        String viewName = userController.findAll(model);

        assertThat(viewName).isNotNull();
        assertThat(model.containsAttribute("users")).isTrue();

        List<User> users = (List<User>) model.asMap().get("users");
        assertThat(users).isNotEmpty();
    }

    @Test
    public void createUserForm() {
        String viewName = userController.createUserForm(new User());
        assertThat(viewName).isEqualTo("user-create");
    }

    @Test
    public void createUser() {
        User user = new User();
        when(userService.saveUser(user)).thenReturn(user);

        //RedirectAttributes redirectAttributes = new org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap();

        String viewName = userController.createUser(user);

        assertThat(viewName).isEqualTo("redirect:/users");
    }

    @Test
    public void deleteUser() {
        Long userId = 1L;
        RedirectAttributes redirectAttributes = new org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap();

        String viewName = userController.deleteUser(userId);

        assertThat(viewName).isEqualTo("redirect:/users");
        verify(userService).deleteById(userId);
    }

    @Test
    public void updateUserForm() {
        Long userId = 1L;
        User mockUser = new User();
        when(userService.findById(userId)).thenReturn(mockUser);

        Model model = new ConcurrentModel();//здесь менял

        String viewName = userController.updateUserForm(userId, model);

        assertThat(viewName).isEqualTo("user-update");
        assertThat(model.containsAttribute("user")).isTrue();
        assertThat(model.asMap().get("user")).isEqualTo(mockUser);
    }

    @Test
    public void updateUser() {
        User user = new User();
        when(userService.saveUser(any(User.class))).thenReturn(user);

        String viewName = userController.updateUser(user);

        assertThat(viewName).isEqualTo("redirect:/users");
    }
}

