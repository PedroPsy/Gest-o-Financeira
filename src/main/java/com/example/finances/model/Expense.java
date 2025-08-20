package com.example.finances.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EXPENSE")
public class Expense extends Transaction { }
