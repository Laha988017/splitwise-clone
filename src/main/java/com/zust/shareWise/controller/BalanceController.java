package com.zust.shareWise.controller;

import com.zust.shareWise.dto.BalanceDTO;
import com.zust.shareWise.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/balances")
public class BalanceController {
    @Autowired
    private BalanceService balanceService;

    @GetMapping
    public ResponseEntity<List<BalanceDTO>> getAllBalances() {
        return ResponseEntity.ok(balanceService.calculateDetailedBalances());
    }
}
