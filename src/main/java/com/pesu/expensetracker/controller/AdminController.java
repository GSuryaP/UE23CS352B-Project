package com.pesu.expensetracker.controller;

import com.pesu.expensetracker.model.User;
import com.pesu.expensetracker.service.UserService;
import com.pesu.expensetracker.service.ExpenseService;
import com.pesu.expensetracker.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private SessionManager sessionManager;

    private boolean checkAdminAuth(Model model) {
        if (!sessionManager.isLoggedIn() || !sessionManager.isAdmin()) {
            model.addAttribute("error", "Admin access required");
            return false;
        }
        return true;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        if (!checkAdminAuth(model)) return "redirect:/auth/login";

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("adminUser", sessionManager.getLoggedInUser());
        model.addAttribute("totalUsers", users.size());

        return "admin-dashboard";
    }

    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        if (!checkAdminAuth(model)) return "redirect:/auth/login";

        List<User> users = userService.getAllNonAdminUsers();
        model.addAttribute("users", users);
        model.addAttribute("adminUser", sessionManager.getLoggedInUser());

        return "admin-users";
    }

    @GetMapping("/user/{id}/expenses")
    public String viewUserExpenses(@PathVariable Long id, Model model) {
        if (!checkAdminAuth(model)) return "redirect:/auth/login";

        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/admin/users";
        }

        model.addAttribute("user", user);
        model.addAttribute("expenses", expenseService.getUserExpenses(user));
        model.addAttribute("totalExpenses", expenseService.getTotalExpensesForUser(user));
        model.addAttribute("adminUser", sessionManager.getLoggedInUser());

        return "admin-user-expenses";
    }

    @PostMapping("/user/{id}/toggle-role")
    public String toggleUserRole(@PathVariable Long id, @RequestParam String action, Model model) {
        if (!checkAdminAuth(model)) return "redirect:/auth/login";

        User user = userService.getUserById(id);
        if (user != null && !user.getId().equals(sessionManager.getLoggedInUser().getId())) {
            if ("promote".equals(action)) {
                user.setRole("ADMIN");
            } else if ("demote".equals(action)) {
                user.setRole("USER");
            }
            userService.updateUser(user);
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable Long id, Model model) {
        if (!checkAdminAuth(model)) return "redirect:/auth/login";

        User user = userService.getUserById(id);
        // Cannot delete yourself
        if (user != null && !user.getId().equals(sessionManager.getLoggedInUser().getId())) {
            userService.deleteUser(id);
        }

        return "redirect:/admin/users";
    }
}
