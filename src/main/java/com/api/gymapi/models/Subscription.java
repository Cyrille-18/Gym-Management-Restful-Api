package com.api.gymapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties({"subscriptions"})  // Éviter la boucle infinie
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "PackId", nullable = false)
    private Pack pack;

    private LocalDate startDate;
}