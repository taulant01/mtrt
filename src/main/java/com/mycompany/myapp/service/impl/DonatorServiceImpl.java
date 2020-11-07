package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DonatorService;
import com.mycompany.myapp.domain.Donator;
import com.mycompany.myapp.repository.DonatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Donator}.
 */
@Service
@Transactional
public class DonatorServiceImpl implements DonatorService {

    private final Logger log = LoggerFactory.getLogger(DonatorServiceImpl.class);

    private final DonatorRepository donatorRepository;

    public DonatorServiceImpl(DonatorRepository donatorRepository) {
        this.donatorRepository = donatorRepository;
    }

    @Override
    public Donator save(Donator donator) {
        log.debug("Request to save Donator : {}", donator);
        return donatorRepository.save(donator);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Donator> findAll(Pageable pageable) {
        log.debug("Request to get all Donators");
        return donatorRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Donator> findOne(Long id) {
        log.debug("Request to get Donator : {}", id);
        return donatorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Donator : {}", id);
        donatorRepository.deleteById(id);
    }
}
