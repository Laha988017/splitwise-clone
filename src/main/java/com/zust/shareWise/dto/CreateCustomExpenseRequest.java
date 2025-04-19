package com.zust.shareWise.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateCustomExpenseRequest {
    private String description;
    private Double totalAmount;
    private Long createdBy;
    private Long paidBy;
    private List<CustomSplit> splits;
}
