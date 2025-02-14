package com.api.gymapi.controller;

import com.api.gymapi.Dtos.SubscriptionRequestDto;
import com.api.gymapi.models.Subscription;
import com.api.gymapi.service.ISubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final ISubscriptionService subscriptionService;

    public SubscriptionController(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionRequestDto request) {
        try {
            Subscription createdSubscription = subscriptionService.createSubscription(request);

            // Vérifie si le client est bien inclus dans la réponse
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
}
