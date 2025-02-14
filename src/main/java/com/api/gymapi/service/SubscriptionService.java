package com.api.gymapi.service;

import com.api.gymapi.Dtos.SubscriptionRequestDto;
import com.api.gymapi.models.Customer;
import com.api.gymapi.models.Pack;
import com.api.gymapi.models.Subscription;
import com.api.gymapi.repository.CustomerRepository;
import com.api.gymapi.repository.PackRepository;
import com.api.gymapi.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService implements ISubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final PackRepository packRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, CustomerRepository customerRepository, PackRepository packRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.packRepository = packRepository;
    }

    @Override
    public Subscription createSubscription(SubscriptionRequestDto request) {
        Optional<Customer> customerOpt = customerRepository.findById(request.getCustomerId());
        Optional<Pack> packOpt = packRepository.findById(request.getPackId());

        if (customerOpt.isEmpty() || packOpt.isEmpty()) {
            throw new IllegalArgumentException("Client ou Pack introuvable");
        }

        Customer customer = customerOpt.get();

        // Vérifier si le client a déjà une souscription active
        if (customer.isActiveSubscription()) {
            throw new IllegalStateException("Le client a déjà une souscription en cours.");
        }

        Subscription subscription = new Subscription();
        subscription.setCustomer(customer);
        subscription.setPack(packOpt.get());
        subscription.setStartDate(LocalDate.now());

        // Activer la souscription pour ce client
        customer.setActiveSubscription(true);
        customerRepository.save(customer);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public Subscription updateSubscription(Long subscriptionId, SubscriptionRequestDto subscriptionDto) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Souscription non trouvée"));

        // Vérifier si la souscription est encore active
        LocalDate endDate = subscription.getStartDate().plusMonths(subscription.getPack().getDurationMonths());
        if (LocalDate.now().isAfter(endDate)) {
            throw new IllegalStateException("La souscription est expirée et ne peut pas être modifiée.");
        }

        // Modification du client si fourni
        if (subscriptionDto.getCustomerId() != null) {
            Customer newCustomer = customerRepository.findById(subscriptionDto.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
            subscription.setCustomer(newCustomer);
        }

        // Modification du pack si fourni
        if (subscriptionDto.getPackId() != null) {
            Pack newPack = packRepository.findById(subscriptionDto.getPackId())
                    .orElseThrow(() -> new IllegalArgumentException("Pack non trouvé"));
            subscription.setPack(newPack);
        }

        return subscriptionRepository.save(subscription);
    }
    @Override
    public boolean deleteSubscription(Long id) {
        return subscriptionRepository.findById(id).map(subscription -> {
            Customer customer = subscription.getCustomer();

            // Désactiver la souscription si c'était la seule en cours
            customer.setActiveSubscription(false);
            customerRepository.save(customer);

            subscriptionRepository.delete(subscription);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Subscription> getSubscriptionsByCustomer(Long customerId) {
        return subscriptionRepository.findAll().stream()
                .filter(subscription -> subscription.getCustomer().getUserId() == customerId)
                .toList();
    }
}
