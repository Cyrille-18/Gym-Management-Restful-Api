package com.api.gymapi.service;

import com.api.gymapi.models.Subscription;

import java.util.List;

public interface ISubscriptionService {
    public Subscription getSubscription(long id);
    public List<Subscription> getSubscriptions();
    public boolean addSubscription(Subscription subscription);
    public boolean deleteSubscription(long id);
}
