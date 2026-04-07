package com.pesu.expensetracker.controller;

import com.pesu.expensetracker.model.Expense;
import com.pesu.expensetracker.service.ExpenseService;
import com.pesu.expensetracker.util.SessionManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.util.*;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private SessionManager sessionManager;

    private boolean checkAuth(Model model) {
        if (!sessionManager.isLoggedIn()) {
            return false;
        }
        model.addAttribute("user", sessionManager.getLoggedInUser());
        return true;
    }

    @GetMapping("/")
    public String home(Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/auth/login";
        }
        model.addAttribute("user", sessionManager.getLoggedInUser());
        return "home";
    }

    @GetMapping("/add")
    public String showAddExpenseForm(Model model) {
        if (!checkAuth(model)) return "redirect:/auth/login";
        if (sessionManager.isAdmin()) return "redirect:/admin/dashboard";
        
        model.addAttribute("expense", new Expense());
        return "add-expense";
    }

    @PostMapping("/add")
    public String saveExpense(@Valid @ModelAttribute Expense expense,
                              BindingResult result) {

        if (!sessionManager.isLoggedIn()) {
            return "redirect:/auth/login";
        }

        if (result.hasErrors()) {
            return "add-expense";
        }

        expenseService.addExpense(expense);
        return "redirect:/view";
    }

   @GetMapping("/view")
    public String viewExpenses(Model model) {
        if (!checkAuth(model)) return "redirect:/auth/login";
        if (sessionManager.isAdmin()) return "redirect:/admin/dashboard";
        
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        model.addAttribute("user", sessionManager.getLoggedInUser());

        Set<String> categories = expenses.stream()
                .map(Expense::getCategory)
                .collect(Collectors.toSet());

        model.addAttribute("categories", categories);

        return "view-expenses";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/auth/login";
        }
        
        expenseService.deleteExpense(id);
        return "redirect:/view";
    }

    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        if (!checkAuth(model)) return "redirect:/auth/login";
        
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            return "redirect:/view";
        }
        
        model.addAttribute("expense", expense);
        return "edit-expenses";
    }

    @PostMapping("/update")
    public String updateExpense(@ModelAttribute Expense expense) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/auth/login";
        }
        
        expenseService.updateExpense(expense);
        return "redirect:/view";
    }

    @GetMapping("/summary")
    public String showSummary(Model model) {
        if (sessionManager.isAdmin()) return "redirect:/admin/dashboard";
        if (!checkAuth(model)) return "redirect:/auth/login";
        
        List<Expense> expenses = expenseService.getAllExpenses();
        double total = expenseService.getTotalExpenses();
        
        // Calculate average per transaction
        double average = expenses.isEmpty() ? 0 : total / expenses.size();
        
        // Count unique categories
        long categoryCount = expenses.stream()
                .map(Expense::getCategory)
                .distinct()
                .count();
        
        model.addAttribute("total", String.format("%.2f", total));
        model.addAttribute("average", String.format("%.2f", average));
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("expenseCount", expenses.size());
        model.addAttribute("user", sessionManager.getLoggedInUser());
        return "summary";
    }

    @GetMapping("/chart")
    public String showChart(Model model) {
        if (!checkAuth(model)) return "redirect:/auth/login";

        List<Expense> expenses = expenseService.getAllExpenses();
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense e : expenses) {
            categoryTotals.merge(e.getCategory(), e.getAmount(), Double::sum);
        }

        model.addAttribute("categories", categoryTotals.keySet());
        model.addAttribute("amounts", categoryTotals.values());
        model.addAttribute("user", sessionManager.getLoggedInUser());

        return "chart";
    }
}
