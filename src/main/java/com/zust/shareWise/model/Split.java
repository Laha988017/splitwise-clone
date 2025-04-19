package com.zust.shareWise.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Split {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Expense expense;

    @ManyToOne
    private User user;

    private Double amountOwed;
    // Getters and setters
}
