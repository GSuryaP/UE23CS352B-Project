package com.pesu.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pesu.expensetracker.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
