package com.plan.vr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.plan.vr.domain.Kriterijum;
import com.plan.vr.service.KriterijumService;
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
 * REST controller for managing Kriterijum.
 */
@RestController
@RequestMapping("/api")
public class KriterijumResource {

    private final Logger log = LoggerFactory.getLogger(KriterijumResource.class);

    private static final String ENTITY_NAME = "kriterijum";

    private final KriterijumService kriterijumService;

    public KriterijumResource(KriterijumService kriterijumService) {
        this.kriterijumService = kriterijumService;
    }

    /**
     * POST  /kriterijums : Create a new kriterijum.
     *
     * @param kriterijum the kriterijum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kriterijum, or with status 400 (Bad Request) if the kriterijum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kriterijums")
    @Timed
    public ResponseEntity<Kriterijum> createKriterijum(@Valid @RequestBody Kriterijum kriterijum) throws URISyntaxException {
        log.debug("REST request to save Kriterijum : {}", kriterijum);
        if (kriterijum.getId() != null) {
            throw new BadRequestAlertException("A new kriterijum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kriterijum result = kriterijumService.save(kriterijum);
        return ResponseEntity.created(new URI("/api/kriterijums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kriterijums : Updates an existing kriterijum.
     *
     * @param kriterijum the kriterijum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kriterijum,
     * or with status 400 (Bad Request) if the kriterijum is not valid,
     * or with status 500 (Internal Server Error) if the kriterijum couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kriterijums")
    @Timed
    public ResponseEntity<Kriterijum> updateKriterijum(@Valid @RequestBody Kriterijum kriterijum) throws URISyntaxException {
        log.debug("REST request to update Kriterijum : {}", kriterijum);
        if (kriterijum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Kriterijum result = kriterijumService.save(kriterijum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kriterijum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kriterijums : get all the kriterijums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kriterijums in body
     */
    @GetMapping("/kriterijums")
    @Timed
    public List<Kriterijum> getAllKriterijums() {
        log.debug("REST request to get all Kriterijums");
        return kriterijumService.findAll();
    }

    @GetMapping("/kriterijums/ap/{id}")
    @Timed
    public List<Kriterijum> getAllKriterijumsByAkcioniPlanId(@PathVariable Long id){
        return kriterijumService.findByAkcioniPlan_Id(id);
    }


    /**
     * GET  /kriterijums/:id : get the "id" kriterijum.
     *
     * @param id the id of the kriterijum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kriterijum, or with status 404 (Not Found)
     */
    @GetMapping("/kriterijums/{id}")
    @Timed
    public ResponseEntity<Kriterijum> getKriterijum(@PathVariable Long id) {
        log.debug("REST request to get Kriterijum : {}", id);
        Optional<Kriterijum> kriterijum = kriterijumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kriterijum);
    }

    /**
     * DELETE  /kriterijums/:id : delete the "id" kriterijum.
     *
     * @param id the id of the kriterijum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kriterijums/{id}")
    @Timed
    public ResponseEntity<Void> deleteKriterijum(@PathVariable Long id) {
        log.debug("REST request to delete Kriterijum : {}", id);
        kriterijumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
