package com.zust.shareWise.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double totalAmount;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User paidBy;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Split> splits = new ArrayList<>();
    // Getters and setters
}
