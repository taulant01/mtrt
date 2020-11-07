package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TreatService;
import com.mycompany.myapp.domain.Treat;
import com.mycompany.myapp.repository.TreatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Treat}.
 */
@Service
@Transactional
public class TreatServiceImpl implements TreatService {

    private final Logger log = LoggerFactory.getLogger(TreatServiceImpl.class);

    private final TreatRepository treatRepository;

    public TreatServiceImpl(TreatRepository treatRepository) {
        this.treatRepository = treatRepository;
    }

    @Override
    public Treat save(Treat treat) {
        log.debug("Request to save Treat : {}", treat);
        return treatRepository.save(treat);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Treat> findAll(Pageable pageable) {
        log.debug("Request to get all Treats");
        return treatRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Treat> findOne(Long id) {
        log.debug("Request to get Treat : {}", id);
        return treatRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Treat : {}", id);
        treatRepository.deleteById(id);
    }
}
