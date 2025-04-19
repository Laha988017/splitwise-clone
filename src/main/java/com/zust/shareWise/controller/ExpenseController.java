package com.zust.shareWise.controller;

import com.zust.shareWise.dto.CreateCustomExpenseRequest;
import com.zust.shareWise.dto.CreateExpenseRequest;
import com.zust.shareWise.model.Expense;
import com.zust.shareWise.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/create")
    public ResponseEntity<Expense> createExpense(@RequestBody CreateExpenseRequest request) {
        return new ResponseEntity<>(expenseService.createExpense(request), HttpStatus.CREATED);
    }

    @PostMapping("/custom")
    public ResponseEntity<Expense> createCustomExpense(@RequestBody CreateCustomExpenseRequest request) {
        return new ResponseEntity<>(expenseService.createCustomExpense(request), HttpStatus.CREATED);
    }
}
