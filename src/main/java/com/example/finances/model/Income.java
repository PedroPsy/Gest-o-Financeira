package com.example.finances.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INCOME")
public class Income extends Transaction { }
