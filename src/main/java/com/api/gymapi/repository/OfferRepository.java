package com.api.gymapi.repository;

import com.api.gymapi.models.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Pack, Long> {
}
