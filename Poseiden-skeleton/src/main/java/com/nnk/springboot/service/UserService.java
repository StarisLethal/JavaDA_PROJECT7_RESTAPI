package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    //Regex pattern who ensure at least one letter one caps one digit and one symbol
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,}$";
    // Pattern initialize
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     *
     * @param password from domain User
     * @return a boolean that checks if the password matches the regex
     */
    public boolean passwordControl(String password) {
        return pattern.matcher(password).matches();
    }

    public Optional<User> get(Integer id) {
        return userRepository.findById(id);
    }

    public Iterable<User> list() {
        return userRepository.findAll();
    }

    public String getFullname(String username) {
        return userRepository.findFullname(username);
    }

    /**
     * Save of User from domain with a password control
     *
     * @param user from domain
     * @return save of user or an exception if password don't follow the rule
     */
    public User save(User user) {
        if (passwordControl(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("The password must contain at least one lowercase letter, one uppercase letter, one number and one symbol.");
        }
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
