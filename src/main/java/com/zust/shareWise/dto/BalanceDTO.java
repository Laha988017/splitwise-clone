package com.zust.shareWise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceDTO {
    private String borrowerName;
    private String lenderName;
    private Double amount;
}