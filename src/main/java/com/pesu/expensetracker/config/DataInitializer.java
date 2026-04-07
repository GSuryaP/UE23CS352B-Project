package com.pesu.expensetracker.config;

import com.pesu.expensetracker.model.User;
import com.pesu.expensetracker.repository.UserRepository;
import com.pesu.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User("admin", userService.hashPassword("admin123"), "admin@expensetracker.com");
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
            System.out.println("Admin user created: username=admin, password=admin123");
        }
    }
}
