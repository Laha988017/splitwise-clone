package com.zust.shareWise.service;

import com.zust.shareWise.dto.CreateCustomExpenseRequest;
import com.zust.shareWise.dto.CreateExpenseRequest;
import com.zust.shareWise.dto.CustomSplit;
import com.zust.shareWise.model.Expense;
import com.zust.shareWise.model.Split;
import com.zust.shareWise.model.User;
import com.zust.shareWise.repository.ExpenseRepository;
import com.zust.shareWise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private ExpenseRepository expenseRepository;

    public Expense createExpense(CreateExpenseRequest request) {
        User creator = userRepository.findById(request.getCreatedBy()).orElseThrow();
        User payer = userRepository.findById(request.getPaidBy()).orElseThrow();

        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setTotalAmount(request.getTotalAmount());
        expense.setCreatedBy(creator);
        expense.setPaidBy(payer);

        double share = request.getTotalAmount() / request.getUserIds().size();
        List<Split> splits = new ArrayList<>();
        for (Long userId : request.getUserIds()) {
            User user = userRepository.findById(userId).orElseThrow();
            Split split = new Split();
            split.setUser(user);
            split.setAmountOwed(share);
            split.setExpense(expense);
            splits.add(split);
        }

        expense.setSplits(splits);
        return expenseRepository.save(expense);
    }

    public Expense createCustomExpense(CreateCustomExpenseRequest request) {
        User creator = userRepository.findById(request.getCreatedBy()).orElseThrow();
        User payer = userRepository.findById(request.getPaidBy()).orElseThrow();

        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setTotalAmount(request.getTotalAmount());
        expense.setCreatedBy(creator);
        expense.setPaidBy(payer);

        List<Split> splits = new ArrayList<>();
        for (CustomSplit cs : request.getSplits()) {
            User user = userRepository.findById(cs.getUserId()).orElseThrow();
            Split split = new Split();
            split.setUser(user);
            split.setAmountOwed(cs.getAmountOwed());
            split.setExpense(expense);
            splits.add(split);
        }

        expense.setSplits(splits);
        return expenseRepository.save(expense);
    }
}
