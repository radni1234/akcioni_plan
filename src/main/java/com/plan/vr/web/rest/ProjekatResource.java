package com.plan.vr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.plan.vr.domain.Projekat;
import com.plan.vr.repository.ProjekatRepository;
import com.plan.vr.web.rest.errors.BadRequestAlertException;
import com.plan.vr.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Projekat.
 */
@RestController
@RequestMapping("/api")
public class ProjekatResource {

    private final Logger log = LoggerFactory.getLogger(ProjekatResource.class);

    private static final String ENTITY_NAME = "projekat";

    private final ProjekatRepository projekatRepository;

    public ProjekatResource(ProjekatRepository projekatRepository) {
        this.projekatRepository = projekatRepository;
    }

    /**
     * POST  /projekats : Create a new projekat.
     *
     * @param projekat the projekat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projekat, or with status 400 (Bad Request) if the projekat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projekats")
    @Timed
    public ResponseEntity<Projekat> createProjekat(@Valid @RequestBody Projekat projekat) throws URISyntaxException {
        log.debug("REST request to save Projekat : {}", projekat);
        if (projekat.getId() != null) {
            throw new BadRequestAlertException("A new projekat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Projekat result = projekatRepository.save(projekat);
        return ResponseEntity.created(new URI("/api/projekats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projekats : Updates an existing projekat.
     *
     * @param projekat the projekat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projekat,
     * or with status 400 (Bad Request) if the projekat is not valid,
     * or with status 500 (Internal Server Error) if the projekat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projekats")
    @Timed
    public ResponseEntity<Projekat> updateProjekat(@Valid @RequestBody Projekat projekat) throws URISyntaxException {
        log.debug("REST request to update Projekat : {}", projekat);
        if (projekat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Projekat result = projekatRepository.save(projekat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projekat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projekats : get all the projekats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projekats in body
     */
    @GetMapping("/projekats")
    @Timed
    public List<Projekat> getAllProjekats() {
        log.debug("REST request to get all Projekats");
        return projekatRepository.findAll();
    }

    /**
     * GET  /projekats/:id : get the "id" projekat.
     *
     * @param id the id of the projekat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projekat, or with status 404 (Not Found)
     */
    @GetMapping("/projekats/{id}")
    @Timed
    public ResponseEntity<Projekat> getProjekat(@PathVariable Long id) {
        log.debug("REST request to get Projekat : {}", id);
        Optional<Projekat> projekat = projekatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projekat);
    }

    /**
     * DELETE  /projekats/:id : delete the "id" projekat.
     *
     * @param id the id of the projekat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projekats/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjekat(@PathVariable Long id) {
        log.debug("REST request to delete Projekat : {}", id);

        projekatRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
