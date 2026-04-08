package com.pesu.expensetracker.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pesu.expensetracker.model.Expense;
import com.pesu.expensetracker.model.User;
import com.pesu.expensetracker.repository.ExpenseRepository;
import com.pesu.expensetracker.util.SessionManager;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SessionManager sessionManager;

    public void addExpense(Expense expense) {
        // Set the current user as the owner of the expense
        User currentUser = sessionManager.getLoggedInUser();
        if (currentUser != null) {
            expense.setUser(currentUser);
            expenseRepository.save(expense);
        }
    }

    public List<Expense> getAllExpenses() {
        User currentUser = sessionManager.getLoggedInUser();
        if (currentUser == null) {
            return new ArrayList<>();
        }
        // If admin, return all expenses; otherwise return only user's expenses
        if (currentUser.isAdmin()) {
            return expenseRepository.findAll();
        }
        return expenseRepository.findByUser(currentUser);
    }

    public List<Expense> getUserExpenses(User user) {
        return expenseRepository.findByUser(user);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense != null) {
            User currentUser = sessionManager.getLoggedInUser();
            // Allow deletion if user owns the expense or is admin
            if (currentUser != null && (currentUser.isAdmin() || expense.getUser().getId().equals(currentUser.getId()))) {
                expenseRepository.deleteById(id);
            }
        }
    }

    public Expense getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense != null) {
            User currentUser = sessionManager.getLoggedInUser();
            // Allow access if user owns the expense or is admin
            if (currentUser != null && (currentUser.isAdmin() || expense.getUser().getId().equals(currentUser.getId()))) {
                return expense;
            }
        }
        return null;
    }

    public void updateExpense(Expense expense) {
        Expense existing = expenseRepository.findById(expense.getId()).orElse(null);
        if (existing != null) {
            User currentUser = sessionManager.getLoggedInUser();
            // Allow update if user owns the expense or is admin
            if (currentUser != null && (currentUser.isAdmin() || existing.getUser().getId().equals(currentUser.getId()))) {
                expense.setUser(existing.getUser());
                expenseRepository.save(expense);
            }
        }
    }

    public double getTotalExpenses() {
        return getAllExpenses().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public double getTotalExpensesForUser(User user) {
        return getUserExpenses(user).stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}

