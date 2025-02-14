package com.api.gymapi.service;

import com.api.gymapi.Dtos.SubscriptionRequestDto;
import com.api.gymapi.models.Subscription;
import com.api.gymapi.models.User;

import java.util.List;
import java.util.Optional;

public interface ISubscriptionService {
    public Subscription createSubscription(SubscriptionRequestDto request);
    List<Subscription> getAllSubscriptions();
    Optional<Subscription> getSubscriptionById(Long id);
    public Subscription updateSubscription(Long subscriptionId, SubscriptionRequestDto subscriptionDto);
    boolean deleteSubscription(Long id);
    List<Subscription> getSubscriptionsByCustomer(Long customerId);
    public Optional<Subscription> getCurrentSubscriptionForUser(User user);
}
