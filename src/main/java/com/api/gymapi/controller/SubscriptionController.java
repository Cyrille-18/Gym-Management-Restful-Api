package com.api.gymapi.controller;

import com.api.gymapi.Dtos.SubscriptionRequestDto;
import com.api.gymapi.models.Subscription;
import com.api.gymapi.models.User;
import com.api.gymapi.repository.UserRepository;
import com.api.gymapi.service.ISubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final ISubscriptionService subscriptionService;
    private final UserRepository userRepository;  // Assurez-vous d'avoir un UserRepository injecté

    public SubscriptionController(ISubscriptionService subscriptionService, UserRepository userRepository) {
        this.subscriptionService = subscriptionService;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionRequestDto request) {
        try {
            Subscription createdSubscription = subscriptionService.createSubscription(request);
            return ResponseEntity.ok(createdSubscription);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    @PreAuthorize("isAuthenticated()")
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("getone/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        return subscriptionService.getSubscriptionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateSubscription(
            @PathVariable Long id,
            @RequestBody SubscriptionRequestDto subscriptionDto) {
        try {
            Subscription updatedSubscription = subscriptionService.updateSubscription(id, subscriptionDto);
            return ResponseEntity.ok(updatedSubscription);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        return subscriptionService.deleteSubscription(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("isAuthenticated()")
    public List<Subscription> getSubscriptionsByCustomer(@PathVariable Long customerId) {
        return subscriptionService.getSubscriptionsByCustomer(customerId);
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Subscription> getCurrentSubscription() {
        // Récupère le nom de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Récupère l'utilisateur basé sur le nom d'utilisateur
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            // Appelle la méthode du service pour obtenir la souscription active
            Optional<Subscription> currentSubscription = subscriptionService.getCurrentSubscriptionForUser(user.get());

            // Si une souscription active est trouvée, elle est retournée
            return currentSubscription
                    .map(ResponseEntity::ok)  // Renvoie la souscription active dans la réponse
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Si aucune souscription active, renvoie un 404
        } else {
            // Si l'utilisateur n'est pas trouvé, retourne un 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
