package com.api.gymapi.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "packId", nullable = false)
    private Pack pack;

    private LocalDate startDate;
}