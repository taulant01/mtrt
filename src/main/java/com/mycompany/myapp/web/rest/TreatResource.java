package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Treat;
import com.mycompany.myapp.service.TreatService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Treat}.
 */
@RestController
@RequestMapping("/api")
public class TreatResource {

    private final Logger log = LoggerFactory.getLogger(TreatResource.class);

    private static final String ENTITY_NAME = "treat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TreatService treatService;

    public TreatResource(TreatService treatService) {
        this.treatService = treatService;
    }

    /**
     * {@code POST  /treats} : Create a new treat.
     *
     * @param treat the treat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new treat, or with status {@code 400 (Bad Request)} if the treat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/treats")
    public ResponseEntity<Treat> createTreat(@RequestBody Treat treat) throws URISyntaxException {
        log.debug("REST request to save Treat : {}", treat);
        if (treat.getId() != null) {
            throw new BadRequestAlertException("A new treat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Treat result = treatService.save(treat);
        return ResponseEntity.created(new URI("/api/treats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /treats} : Updates an existing treat.
     *
     * @param treat the treat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated treat,
     * or with status {@code 400 (Bad Request)} if the treat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the treat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/treats")
    public ResponseEntity<Treat> updateTreat(@RequestBody Treat treat) throws URISyntaxException {
        log.debug("REST request to update Treat : {}", treat);
        if (treat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Treat result = treatService.save(treat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, treat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /treats} : get all the treats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of treats in body.
     */
    @GetMapping("/treats")
    public ResponseEntity<List<Treat>> getAllTreats(Pageable pageable) {
        log.debug("REST request to get a page of Treats");
        Page<Treat> page = treatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /treats/:id} : get the "id" treat.
     *
     * @param id the id of the treat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the treat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/treats/{id}")
    public ResponseEntity<Treat> getTreat(@PathVariable Long id) {
        log.debug("REST request to get Treat : {}", id);
        Optional<Treat> treat = treatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treat);
    }

    /**
     * {@code DELETE  /treats/:id} : delete the "id" treat.
     *
     * @param id the id of the treat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/treats/{id}")
    public ResponseEntity<Void> deleteTreat(@PathVariable Long id) {
        log.debug("REST request to delete Treat : {}", id);
        treatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
