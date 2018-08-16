package com.plan.vr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.plan.vr.domain.AkcioniPlan;
import com.plan.vr.service.AkcioniPlanService;
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
 * REST controller for managing AkcioniPlan.
 */
@RestController
@RequestMapping("/api")
public class AkcioniPlanResource {

    private final Logger log = LoggerFactory.getLogger(AkcioniPlanResource.class);

    private static final String ENTITY_NAME = "akcioniPlan";

    private final AkcioniPlanService akcioniPlanService;

    public AkcioniPlanResource(AkcioniPlanService akcioniPlanService) {
        this.akcioniPlanService = akcioniPlanService;
    }

    /**
     * POST  /akcioni-plans : Create a new akcioniPlan.
     *
     * @param akcioniPlan the akcioniPlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new akcioniPlan, or with status 400 (Bad Request) if the akcioniPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/akcioni-plans")
    @Timed
    public ResponseEntity<AkcioniPlan> createAkcioniPlan(@Valid @RequestBody AkcioniPlan akcioniPlan) throws URISyntaxException {
        log.debug("REST request to save AkcioniPlan : {}", akcioniPlan);
        if (akcioniPlan.getId() != null) {
            throw new BadRequestAlertException("A new akcioniPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkcioniPlan result = akcioniPlanService.save(akcioniPlan);
        return ResponseEntity.created(new URI("/api/akcioni-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /akcioni-plans : Updates an existing akcioniPlan.
     *
     * @param akcioniPlan the akcioniPlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated akcioniPlan,
     * or with status 400 (Bad Request) if the akcioniPlan is not valid,
     * or with status 500 (Internal Server Error) if the akcioniPlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/akcioni-plans")
    @Timed
    public ResponseEntity<AkcioniPlan> updateAkcioniPlan(@Valid @RequestBody AkcioniPlan akcioniPlan) throws URISyntaxException {
        log.debug("REST request to update AkcioniPlan : {}", akcioniPlan);
        if (akcioniPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AkcioniPlan result = akcioniPlanService.save(akcioniPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, akcioniPlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /akcioni-plans : get all the akcioniPlans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of akcioniPlans in body
     */
    @GetMapping("/akcioni-plans")
    @Timed
    public List<AkcioniPlan> getAllAkcioniPlans() {
        log.debug("REST request to get all AkcioniPlans");
        return akcioniPlanService.findAll();
    }

    /**
     * GET  /akcioni-plans/:id : get the "id" akcioniPlan.
     *
     * @param id the id of the akcioniPlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the akcioniPlan, or with status 404 (Not Found)
     */
    @GetMapping("/akcioni-plans/{id}")
    @Timed
    public ResponseEntity<AkcioniPlan> getAkcioniPlan(@PathVariable Long id) {
        log.debug("REST request to get AkcioniPlan : {}", id);
        Optional<AkcioniPlan> akcioniPlan = akcioniPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(akcioniPlan);
    }

    /**
     * DELETE  /akcioni-plans/:id : delete the "id" akcioniPlan.
     *
     * @param id the id of the akcioniPlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/akcioni-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteAkcioniPlan(@PathVariable Long id) {
        log.debug("REST request to delete AkcioniPlan : {}", id);
        akcioniPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
