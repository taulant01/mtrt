package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Donator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Donator}.
 */
public interface DonatorService {

    /**
     * Save a donator.
     *
     * @param donator the entity to save.
     * @return the persisted entity.
     */
    Donator save(Donator donator);

    /**
     * Get all the donators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Donator> findAll(Pageable pageable);


    /**
     * Get the "id" donator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Donator> findOne(Long id);

    /**
     * Delete the "id" donator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
