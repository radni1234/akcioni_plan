package com.plan.vr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.plan.vr.domain.AdminKriterijum;
import com.plan.vr.repository.AdminKriterijumRepository;
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
 * REST controller for managing AdminKriterijum.
 */
@RestController
@RequestMapping("/api")
public class AdminKriterijumResource {

    private final Logger log = LoggerFactory.getLogger(AdminKriterijumResource.class);

    private static final String ENTITY_NAME = "adminKriterijum";

    private final AdminKriterijumRepository adminKriterijumRepository;

    public AdminKriterijumResource(AdminKriterijumRepository adminKriterijumRepository) {
        this.adminKriterijumRepository = adminKriterijumRepository;
    }

    /**
     * POST  /admin-kriterijums : Create a new adminKriterijum.
     *
     * @param adminKriterijum the adminKriterijum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adminKriterijum, or with status 400 (Bad Request) if the adminKriterijum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/admin-kriterijums")
    @Timed
    public ResponseEntity<AdminKriterijum> createAdminKriterijum(@Valid @RequestBody AdminKriterijum adminKriterijum) throws URISyntaxException {
        log.debug("REST request to save AdminKriterijum : {}", adminKriterijum);
        if (adminKriterijum.getId() != null) {
            throw new BadRequestAlertException("A new adminKriterijum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminKriterijum result = adminKriterijumRepository.save(adminKriterijum);
        return ResponseEntity.created(new URI("/api/admin-kriterijums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /admin-kriterijums : Updates an existing adminKriterijum.
     *
     * @param adminKriterijum the adminKriterijum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adminKriterijum,
     * or with status 400 (Bad Request) if the adminKriterijum is not valid,
     * or with status 500 (Internal Server Error) if the adminKriterijum couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/admin-kriterijums")
    @Timed
    public ResponseEntity<AdminKriterijum> updateAdminKriterijum(@Valid @RequestBody AdminKriterijum adminKriterijum) throws URISyntaxException {
        log.debug("REST request to update AdminKriterijum : {}", adminKriterijum);
        if (adminKriterijum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdminKriterijum result = adminKriterijumRepository.save(adminKriterijum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adminKriterijum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /admin-kriterijums : get all the adminKriterijums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of adminKriterijums in body
     */
    @GetMapping("/admin-kriterijums")
    @Timed
    public List<AdminKriterijum> getAllAdminKriterijums() {
        log.debug("REST request to get all AdminKriterijums");
        return adminKriterijumRepository.findAll();
    }

    /**
     * GET  /admin-kriterijums/:id : get the "id" adminKriterijum.
     *
     * @param id the id of the adminKriterijum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adminKriterijum, or with status 404 (Not Found)
     */
    @GetMapping("/admin-kriterijums/{id}")
    @Timed
    public ResponseEntity<AdminKriterijum> getAdminKriterijum(@PathVariable Long id) {
        log.debug("REST request to get AdminKriterijum : {}", id);
        Optional<AdminKriterijum> adminKriterijum = adminKriterijumRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adminKriterijum);
    }

    /**
     * DELETE  /admin-kriterijums/:id : delete the "id" adminKriterijum.
     *
     * @param id the id of the adminKriterijum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/admin-kriterijums/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdminKriterijum(@PathVariable Long id) {
        log.debug("REST request to delete AdminKriterijum : {}", id);

        adminKriterijumRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
