package com.api.gymapi.repository;

import com.api.gymapi.models.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepository extends JpaRepository<Pack, Long> {
}
