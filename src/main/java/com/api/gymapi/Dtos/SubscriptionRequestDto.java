package com.api.gymapi.Dtos;

import lombok.Data;

@Data
public class SubscriptionRequestDto {
    private Long customerId;
    private Long packId;
}
