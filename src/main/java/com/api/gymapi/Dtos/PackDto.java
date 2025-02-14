package com.api.gymapi.Dtos;

import lombok.Data;

@Data
public class PackDto {
    private String PackName;
    private String PackDescription;
    private int durationMonths;
    private double monthlyPrice;
}
