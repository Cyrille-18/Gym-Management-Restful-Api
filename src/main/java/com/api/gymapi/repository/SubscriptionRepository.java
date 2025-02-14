package com.api.gymapi.repository;

import com.api.gymapi.models.Subscription;
import com.api.gymapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>  {
    // Méthode pour récupérer les souscriptions d'un utilisateur spécifique
    List<Subscription> findByCustomer(User customer);
}
