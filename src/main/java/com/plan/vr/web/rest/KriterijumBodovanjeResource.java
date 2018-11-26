package com.plan.vr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.plan.vr.domain.KriterijumBodovanje;
import com.plan.vr.repository.KriterijumBodovanjeRepository;
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
 * REST controller for managing KriterijumBodovanje.
 */
@RestController
@RequestMapping("/api")
public class KriterijumBodovanjeResource {

    private final Logger log = LoggerFactory.getLogger(KriterijumBodovanjeResource.class);

    private static final String ENTITY_NAME = "kriterijumBodovanje";

    private final KriterijumBodovanjeRepository kriterijumBodovanjeRepository;

    public KriterijumBodovanjeResource(KriterijumBodovanjeRepository kriterijumBodovanjeRepository) {
        this.kriterijumBodovanjeRepository = kriterijumBodovanjeRepository;
    }

    /**
     * POST  /kriterijum-bodovanjes : Create a new kriterijumBodovanje.
     *
     * @param kriterijumBodovanje the kriterijumBodovanje to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kriterijumBodovanje, or with status 400 (Bad Request) if the kriterijumBodovanje has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kriterijum-bodovanjes")
    @Timed
    public ResponseEntity<KriterijumBodovanje> createKriterijumBodovanje(@Valid @RequestBody KriterijumBodovanje kriterijumBodovanje) throws URISyntaxException {
        log.debug("REST request to save KriterijumBodovanje : {}", kriterijumBodovanje);
        if (kriterijumBodovanje.getId() != null) {
            throw new BadRequestAlertException("A new kriterijumBodovanje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KriterijumBodovanje result = kriterijumBodovanjeRepository.save(kriterijumBodovanje);
        return ResponseEntity.created(new URI("/api/kriterijum-bodovanjes/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kriterijum-bodovanjes : Updates an existing kriterijumBodovanje.
     *
     * @param kriterijumBodovanje the kriterijumBodovanje to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kriterijumBodovanje,
     * or with status 400 (Bad Request) if the kriterijumBodovanje is not valid,
     * or with status 500 (Internal Server Error) if the kriterijumBodovanje couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kriterijum-bodovanjes")
    @Timed
    public ResponseEntity<KriterijumBodovanje> updateKriterijumBodovanje(@Valid @RequestBody KriterijumBodovanje kriterijumBodovanje) throws URISyntaxException {
        log.debug("REST request to update KriterijumBodovanje : {}", kriterijumBodovanje);
        if (kriterijumBodovanje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KriterijumBodovanje result = kriterijumBodovanjeRepository.save(kriterijumBodovanje);
        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kriterijumBodovanje.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kriterijum-bodovanjes : get all the kriterijumBodovanjes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kriterijumBodovanjes in body
     */
    @GetMapping("/kriterijum-bodovanjes")
    @Timed
    public List<KriterijumBodovanje> getAllKriterijumBodovanjes() {
        log.debug("REST request to get all KriterijumBodovanjes");
        return kriterijumBodovanjeRepository.findAll();
    }

    @GetMapping("/kriterijum-bodovanjes/kriterijum/{id}")
    @Timed
    public List<KriterijumBodovanje> getAllKriterijumBodovanjeByKriterijumId(@PathVariable Long id) {
        return kriterijumBodovanjeRepository.findAllByKriterijum_Id(id);
    }

    @GetMapping("/kriterijum-bodovanjes/ap/{id}")
    @Timed
    public List<KriterijumBodovanje> getAllKriterijumBodovanjeByAkcioniPlanId(@PathVariable Long id) {
        return kriterijumBodovanjeRepository.findAllByKriterijum_AkcioniPlan_Id(id);
    }

    /**
     * GET  /kriterijum-bodovanjes/:id : get the "id" kriterijumBodovanje.
     *
     * @param id the id of the kriterijumBodovanje to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kriterijumBodovanje, or with status 404 (Not Found)
     */
    @GetMapping("/kriterijum-bodovanjes/{id}")
    @Timed
    public ResponseEntity<KriterijumBodovanje> getKriterijumBodovanje(@PathVariable Long id) {
        log.debug("REST request to get KriterijumBodovanje : {}", id);
        Optional<KriterijumBodovanje> kriterijumBodovanje = kriterijumBodovanjeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kriterijumBodovanje);
    }

    /**
     * DELETE  /kriterijum-bodovanjes/:id : delete the "id" kriterijumBodovanje.
     *
     * @param id the id of the kriterijumBodovanje to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kriterijum-bodovanjes/{id}")
    @Timed
    public ResponseEntity<Void> deleteKriterijumBodovanje(@PathVariable Long id) {
        log.debug("REST request to delete KriterijumBodovanje : {}", id);

        kriterijumBodovanjeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
