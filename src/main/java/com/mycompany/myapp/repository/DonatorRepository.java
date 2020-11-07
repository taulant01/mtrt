package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Donator;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Donator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonatorRepository extends JpaRepository<Donator, Long> {
}
