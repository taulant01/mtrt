package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Treat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Treat}.
 */
public interface TreatService {

    /**
     * Save a treat.
     *
     * @param treat the entity to save.
     * @return the persisted entity.
     */
    Treat save(Treat treat);

    /**
     * Get all the treats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Treat> findAll(Pageable pageable);


    /**
     * Get the "id" treat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Treat> findOne(Long id);

    /**
     * Delete the "id" treat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
