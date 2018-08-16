package com.plan.vr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.plan.vr.domain.AdminKriterijumBodovanje;
import com.plan.vr.repository.AdminKriterijumBodovanjeRepository;
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
 * REST controller for managing AdminKriterijumBodovanje.
 */
@RestController
@RequestMapping("/api")
public class AdminKriterijumBodovanjeResource {

    private final Logger log = LoggerFactory.getLogger(AdminKriterijumBodovanjeResource.class);

    private static final String ENTITY_NAME = "adminKriterijumBodovanje";

    private final AdminKriterijumBodovanjeRepository adminKriterijumBodovanjeRepository;

    public AdminKriterijumBodovanjeResource(AdminKriterijumBodovanjeRepository adminKriterijumBodovanjeRepository) {
        this.adminKriterijumBodovanjeRepository = adminKriterijumBodovanjeRepository;
    }

    /**
     * POST  /admin-kriterijum-bodovanjes : Create a new adminKriterijumBodovanje.
     *
     * @param adminKriterijumBodovanje the adminKriterijumBodovanje to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adminKriterijumBodovanje, or with status 400 (Bad Request) if the adminKriterijumBodovanje has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/admin-kriterijum-bodovanjes")
    @Timed
    public ResponseEntity<AdminKriterijumBodovanje> createAdminKriterijumBodovanje(@Valid @RequestBody AdminKriterijumBodovanje adminKriterijumBodovanje) throws URISyntaxException {
        log.debug("REST request to save AdminKriterijumBodovanje : {}", adminKriterijumBodovanje);
        if (adminKriterijumBodovanje.getId() != null) {
            throw new BadRequestAlertException("A new adminKriterijumBodovanje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminKriterijumBodovanje result = adminKriterijumBodovanjeRepository.save(adminKriterijumBodovanje);
        return ResponseEntity.created(new URI("/api/admin-kriterijum-bodovanjes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /admin-kriterijum-bodovanjes : Updates an existing adminKriterijumBodovanje.
     *
     * @param adminKriterijumBodovanje the adminKriterijumBodovanje to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adminKriterijumBodovanje,
     * or with status 400 (Bad Request) if the adminKriterijumBodovanje is not valid,
     * or with status 500 (Internal Server Error) if the adminKriterijumBodovanje couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/admin-kriterijum-bodovanjes")
    @Timed
    public ResponseEntity<AdminKriterijumBodovanje> updateAdminKriterijumBodovanje(@Valid @RequestBody AdminKriterijumBodovanje adminKriterijumBodovanje) throws URISyntaxException {
        log.debug("REST request to update AdminKriterijumBodovanje : {}", adminKriterijumBodovanje);
        if (adminKriterijumBodovanje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdminKriterijumBodovanje result = adminKriterijumBodovanjeRepository.save(adminKriterijumBodovanje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adminKriterijumBodovanje.getId().toString()))
            .body(result);
    }

    /**
     * GET  /admin-kriterijum-bodovanjes : get all the adminKriterijumBodovanjes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of adminKriterijumBodovanjes in body
     */
    @GetMapping("/admin-kriterijum-bodovanjes")
    @Timed
    public List<AdminKriterijumBodovanje> getAllAdminKriterijumBodovanjes() {
        log.debug("REST request to get all AdminKriterijumBodovanjes");
        return adminKriterijumBodovanjeRepository.findAll();
    }

    /**
     * GET  /admin-kriterijum-bodovanjes/:id : get the "id" adminKriterijumBodovanje.
     *
     * @param id the id of the adminKriterijumBodovanje to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adminKriterijumBodovanje, or with status 404 (Not Found)
     */
    @GetMapping("/admin-kriterijum-bodovanjes/{id}")
    @Timed
    public ResponseEntity<AdminKriterijumBodovanje> getAdminKriterijumBodovanje(@PathVariable Long id) {
        log.debug("REST request to get AdminKriterijumBodovanje : {}", id);
        Optional<AdminKriterijumBodovanje> adminKriterijumBodovanje = adminKriterijumBodovanjeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adminKriterijumBodovanje);
    }

    /**
     * DELETE  /admin-kriterijum-bodovanjes/:id : delete the "id" adminKriterijumBodovanje.
     *
     * @param id the id of the adminKriterijumBodovanje to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/admin-kriterijum-bodovanjes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdminKriterijumBodovanje(@PathVariable Long id) {
        log.debug("REST request to delete AdminKriterijumBodovanje : {}", id);

        adminKriterijumBodovanjeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
