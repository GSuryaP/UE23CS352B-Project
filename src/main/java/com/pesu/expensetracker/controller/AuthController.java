package com.pesu.expensetracker.controller;

import com.pesu.expensetracker.model.User;
import com.pesu.expensetracker.service.UserService;
import com.pesu.expensetracker.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.login(username, password);
        
        if (user != null) {
            sessionManager.setLoggedInUser(user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, 
                          @RequestParam String password,
                          @RequestParam String confirmPassword,
                          @RequestParam String email,
                          Model model) {
        
        // Validate inputs
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        if (username.length() < 3) {
            model.addAttribute("error", "Username must be at least 3 characters");
            return "register";
        }

        if (password.length() < 4) {
            model.addAttribute("error", "Password must be at least 4 characters");
            return "register";
        }

        User user = userService.registerUser(username, password, email);
        
        if (user != null) {
            sessionManager.setLoggedInUser(user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Username or email already exists");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        sessionManager.logout();
        return "redirect:/auth/login";
    }
}
