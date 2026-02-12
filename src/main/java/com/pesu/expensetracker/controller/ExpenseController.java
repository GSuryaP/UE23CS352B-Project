package com.pesu.expensetracker.controller;

import com.pesu.expensetracker.model.Expense;
import com.pesu.expensetracker.service.ExpenseService;
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

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/add")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "add-expense";
    }

    @PostMapping("/add")
    public String saveExpense(@Valid @ModelAttribute Expense expense,
                              BindingResult result) {

        if (result.hasErrors()) {
            return "add-expense";
        }

        expenseService.addExpense(expense);
        return "redirect:/view";
    }

   @GetMapping("/view")
    public String viewExpenses(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);

        Set<String> categories = expenses.stream()
                .map(Expense::getCategory)
                .collect(Collectors.toSet());

        model.addAttribute("categories", categories);

        return "view-expenses";
    }


    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/view";
    }

    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        model.addAttribute("expense", expenseService.getExpenseById(id));
        return "edit-expense";
    }

    @PostMapping("/update")
    public String updateExpense(@ModelAttribute Expense expense) {
        expenseService.addExpense(expense);
        return "redirect:/view";
    }

    @GetMapping("/summary")
    public String showSummary(Model model) {
        model.addAttribute("total", expenseService.getTotalExpenses());
        return "summary";
    }

    @GetMapping("/chart")
    public String showChart(Model model) {

        List<Expense> expenses = expenseService.getAllExpenses();
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense e : expenses) {
            categoryTotals.merge(e.getCategory(), e.getAmount(), Double::sum);
        }

        model.addAttribute("categories", categoryTotals.keySet());
        model.addAttribute("amounts", categoryTotals.values());

        return "chart";
    }
}
