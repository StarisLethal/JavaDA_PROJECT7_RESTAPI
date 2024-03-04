package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void testHome() {
        when(userService.list()).thenReturn(new ArrayList<>());

        String viewName = userController.home(model);

        assertEquals("user/list", viewName);
        verify(model).addAttribute(eq("users"), any());
    }

    @Test
    void testAddUser() {
        User user = new User();

        String viewName = userController.addUser(user);

        assertEquals("user/add", viewName);
    }

    @Test
    void testValidateSuccess() {
        User user = new User();
        user.setPassword("Password@1");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.passwordControl(anyString())).thenReturn(true);

        String viewName = userController.validate(user, bindingResult, model);

        assertEquals("redirect:/user/list", viewName);
        verify(userService).save(any(User.class));
    }

    @Test
    void testValidateFailure() {
        User user = new User();
        user.setPassword("weakpassword");

        when(bindingResult.hasErrors()).thenReturn(true);
        when(userService.passwordControl(anyString())).thenReturn(false);

        String viewName = userController.validate(user, bindingResult, model);

        assertEquals("user/add", viewName);
        verify(bindingResult).rejectValue(eq("password"), eq("password.error"), anyString());
    }

    @Test
    void testShowUpdateForm() {
        int userId = 1;
        User user = new User();
        user.setId(userId);

        when(userService.get(userId)).thenReturn(Optional.of(user));

        String viewName = userController.showUpdateForm(userId, model);

        assertEquals("user/update", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testUpdateUserSuccess() {
        int userId = 1;
        User user = new User();
        user.setPassword("Password@1");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.passwordControl(anyString())).thenReturn(true);

        String viewName = userController.updateUser(userId, user, bindingResult, model);

        assertEquals("redirect:/user/list", viewName);
        verify(userService).save(user);
    }

    @Test
    void testDeleteUser() {
        int userId = 1;

        String viewName = userController.deleteUser(userId, model);

        assertEquals("redirect:/user/list", viewName);
        verify(userService).delete(userId);
    }
}