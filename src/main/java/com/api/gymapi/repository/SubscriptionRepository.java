package com.api.gymapi.repository;

import com.api.gymapi.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>  {
}
