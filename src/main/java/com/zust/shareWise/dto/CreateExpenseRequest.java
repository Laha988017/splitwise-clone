package com.zust.shareWise.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateExpenseRequest {
    private String description;
    private Double totalAmount;
    private Long createdBy;
    private Long paidBy;
    private List<Long> userIds;
}
