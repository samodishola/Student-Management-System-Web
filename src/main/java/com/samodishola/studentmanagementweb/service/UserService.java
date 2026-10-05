package com.samodishola.studentmanagementweb.service;

import com.samodishola.studentmanagementweb.entity.User;
import com.samodishola.studentmanagementweb.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username,
                           String password,
                           String role,
                           String matricNumber) {

        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setMatricNumber(matricNumber);

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void updatePassword(String username, String newPassword) {

        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user != null) {

            user.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(user);
        }
    }

    public User saveUser(User user) {

        return userRepository.save(user);
    }

}