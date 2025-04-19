package com.zust.shareWise.service;

import com.zust.shareWise.dto.BalanceDTO;
import com.zust.shareWise.model.Expense;
import com.zust.shareWise.model.Split;
import com.zust.shareWise.model.User;
import com.zust.shareWise.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BalanceService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<BalanceDTO> calculateDetailedBalances() {
        Map<Long, Map<Long, Double>> netBalances = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        List<BalanceDTO> results = new ArrayList<>();

        for (Expense expense : expenseRepository.findAll()) {
            User payer = expense.getPaidBy();
            users.put(payer.getId(), payer);
            for (Split split : expense.getSplits()) {
                User borrower = split.getUser();
                users.put(borrower.getId(), borrower);
                if (borrower.getId().equals(payer.getId())) continue;

                netBalances
                    .computeIfAbsent(borrower.getId(), k -> new HashMap<>())
                    .merge(payer.getId(), split.getAmountOwed(), Double::sum);
            }
        }

        // Net the balances to avoid duplicate reciprocals
        Set<String> processed = new HashSet<>();

        for (Map.Entry<Long, Map<Long, Double>> entry : netBalances.entrySet()) {
            Long borrowerId = entry.getKey();
            for (Map.Entry<Long, Double> subEntry : entry.getValue().entrySet()) {
                Long lenderId = subEntry.getKey();
                Double amountOwed = subEntry.getValue();

                // Check reverse debt
                Double reverse = netBalances
                        .getOrDefault(lenderId, new HashMap<>())
                        .getOrDefault(borrowerId, 0.0);

                if (reverse > 0) {
                    if (amountOwed > reverse) {
                        amountOwed -= reverse;
                        netBalances.get(lenderId).remove(borrowerId);
                    } else {
                        amountOwed = 0.0;
                        netBalances.get(lenderId).put(borrowerId, reverse - amountOwed);
                    }
                }

                if (amountOwed > 0) {
                    String key = borrowerId + "-" + lenderId;
                    String reverseKey = lenderId + "-" + borrowerId;
                    if (!processed.contains(key) && !processed.contains(reverseKey)) {
                        results.add(new BalanceDTO(
                            users.get(borrowerId).getName(),
                            users.get(lenderId).getName(),
                            Math.round(amountOwed * 100.0) / 100.0
                        ));
                        processed.add(key);
                    }
                }
            }
        }

        return results;
    }
}
