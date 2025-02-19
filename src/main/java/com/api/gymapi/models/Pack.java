package com.api.gymapi.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "packs")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PackId;
    private String PackName;
    private String PackDescription;
    private int durationMonths;
    private double monthlyPrice;
}
