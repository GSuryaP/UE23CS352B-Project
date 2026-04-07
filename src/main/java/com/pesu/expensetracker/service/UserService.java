package com.pesu.expensetracker.service;

import com.pesu.expensetracker.model.User;
import com.pesu.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Simple password hashing (for production, use BCryptPasswordEncoder)
    public String hashPassword(String password) {
        // For simplicity, we'll use a basic hash. In production, use BCryptPasswordEncoder
        return Integer.toString(password.hashCode());
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return hashPassword(plainPassword).equals(hashedPassword);
    }

    public User registerUser(String username, String password, String email) {
        // Check if username or email already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return null; // Username already exists
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return null; // Email already exists
        }

        User user = new User(username, hashPassword(password), email);
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (verifyPassword(password, user.get().getPassword())) {
                return user.get();
            }
        }
        return null;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllNonAdminUsers() {
        return userRepository.findAll().stream()
            .filter(u -> !u.isAdmin())
            .toList();
    }
}
