package com.pesu.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pesu.expensetracker.model.Expense;
import com.pesu.expensetracker.model.User;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserId(Long userId);
}
