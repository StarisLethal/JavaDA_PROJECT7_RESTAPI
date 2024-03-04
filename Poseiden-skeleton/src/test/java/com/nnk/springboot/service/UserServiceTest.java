package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void passwordValide() {
        assertTrue(userService.passwordControl("A1aaaaa?"));
    }

    @Test
    void passwordInvalide() {
        assertFalse(userService.passwordControl("a"));
    }

    @Test
    void get() {
        Optional<User> user = Optional.of(new User());
        Integer idTest = 1;

        when(userRepository.findById(idTest)).thenReturn(user);

        Optional<User> result = userService.get(idTest);

        verify(userRepository).findById(idTest);
        assertEquals(user, result);
    }

    @Test
    void list() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        Iterable<User> result = userService.list();

        verify(userRepository).findAll();
        assertEquals(users, result);
    }

    @Test
    void getFullname() {
        User user = new User();
        String username = "username";
        user.setUsername(username);
        String fullname = "fullname";
        user.setFullname(fullname);

        when(userRepository.findFullname(username)).thenReturn(fullname);

        String result = userService.getFullname(username);

        assertEquals(result, user.getFullname() );
    }

    @Test
    void save() {
        Integer idTest = 1;
        String password = "A1aaaaa?";
        User user = new User();
        user.setId(idTest);
        user.setPassword(password);

        when(userRepository.save(user)).thenReturn(user);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(password);

        User result = userService.save(user);

        verify(userRepository).save(user);
        assertEquals(user, result);
    }

    @Test
    void delete() {
        Integer idTest = 1;

        userService.delete(idTest);

        verify(userRepository).deleteById(idTest);
    }
}