package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Donator;
import com.mycompany.myapp.service.DonatorService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Donator}.
 */
@RestController
@RequestMapping("/api")
public class DonatorResource {

    private final Logger log = LoggerFactory.getLogger(DonatorResource.class);

    private static final String ENTITY_NAME = "donator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonatorService donatorService;

    public DonatorResource(DonatorService donatorService) {
        this.donatorService = donatorService;
    }

    /**
     * {@code POST  /donators} : Create a new donator.
     *
     * @param donator the donator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donator, or with status {@code 400 (Bad Request)} if the donator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donators")
    public ResponseEntity<Donator> createDonator(@RequestBody Donator donator) throws URISyntaxException {
        log.debug("REST request to save Donator : {}", donator);
        if (donator.getId() != null) {
            throw new BadRequestAlertException("A new donator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Donator result = donatorService.save(donator);
        return ResponseEntity.created(new URI("/api/donators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donators} : Updates an existing donator.
     *
     * @param donator the donator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donator,
     * or with status {@code 400 (Bad Request)} if the donator is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donators")
    public ResponseEntity<Donator> updateDonator(@RequestBody Donator donator) throws URISyntaxException {
        log.debug("REST request to update Donator : {}", donator);
        if (donator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Donator result = donatorService.save(donator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donator.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /donators} : get all the donators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donators in body.
     */
    @GetMapping("/donators")
    public ResponseEntity<List<Donator>> getAllDonators(Pageable pageable) {
        log.debug("REST request to get a page of Donators");
        Page<Donator> page = donatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donators/:id} : get the "id" donator.
     *
     * @param id the id of the donator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donators/{id}")
    public ResponseEntity<Donator> getDonator(@PathVariable Long id) {
        log.debug("REST request to get Donator : {}", id);
        Optional<Donator> donator = donatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donator);
    }

    /**
     * {@code DELETE  /donators/:id} : delete the "id" donator.
     *
     * @param id the id of the donator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donators/{id}")
    public ResponseEntity<Void> deleteDonator(@PathVariable Long id) {
        log.debug("REST request to delete Donator : {}", id);
        donatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
