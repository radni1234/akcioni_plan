package com.plan.vr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.plan.vr.domain.ProjekatBodovanje;
import com.plan.vr.repository.ProjekatBodovanjeRepository;
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
 * REST controller for managing ProjekatBodovanje.
 */
@RestController
@RequestMapping("/api")
public class ProjekatBodovanjeResource {

    private final Logger log = LoggerFactory.getLogger(ProjekatBodovanjeResource.class);

    private static final String ENTITY_NAME = "projekatBodovanje";

    private final ProjekatBodovanjeRepository projekatBodovanjeRepository;

    public ProjekatBodovanjeResource(ProjekatBodovanjeRepository projekatBodovanjeRepository) {
        this.projekatBodovanjeRepository = projekatBodovanjeRepository;
    }

    /**
     * POST  /projekat-bodovanjes : Create a new projekatBodovanje.
     *
     * @param projekatBodovanje the projekatBodovanje to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projekatBodovanje, or with status 400 (Bad Request) if the projekatBodovanje has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projekat-bodovanjes")
    @Timed
    public ResponseEntity<ProjekatBodovanje> createProjekatBodovanje(@Valid @RequestBody ProjekatBodovanje projekatBodovanje) throws URISyntaxException {
        log.debug("REST request to save ProjekatBodovanje : {}", projekatBodovanje);
        if (projekatBodovanje.getId() != null) {
            throw new BadRequestAlertException("A new projekatBodovanje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjekatBodovanje result = projekatBodovanjeRepository.save(projekatBodovanje);
        return ResponseEntity.created(new URI("/api/projekat-bodovanjes/" + result.getId()))
            .body(result);
//        return ResponseEntity.created(new URI("/api/projekat-bodovanjes/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
    }

    /**
     * PUT  /projekat-bodovanjes : Updates an existing projekatBodovanje.
     *
     * @param projekatBodovanje the projekatBodovanje to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projekatBodovanje,
     * or with status 400 (Bad Request) if the projekatBodovanje is not valid,
     * or with status 500 (Internal Server Error) if the projekatBodovanje couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projekat-bodovanjes")
    @Timed
    public ResponseEntity<ProjekatBodovanje> updateProjekatBodovanje(@Valid @RequestBody ProjekatBodovanje projekatBodovanje) throws URISyntaxException {
        log.debug("REST request to update ProjekatBodovanje : {}", projekatBodovanje);
        if (projekatBodovanje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjekatBodovanje result = projekatBodovanjeRepository.save(projekatBodovanje);
        return ResponseEntity.ok()
            .body(result);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projekatBodovanje.getId().toString()))
//            .body(result);
    }

    /**
     * GET  /projekat-bodovanjes : get all the projekatBodovanjes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projekatBodovanjes in body
     */
    @GetMapping("/projekat-bodovanjes")
    @Timed
    public List<ProjekatBodovanje> getAllProjekatBodovanjes() {
        log.debug("REST request to get all ProjekatBodovanjes");
        return projekatBodovanjeRepository.findAll();
    }

    @GetMapping("/projekat-bodovanjes/ap/{id}")
    @Timed
    public List<ProjekatBodovanje> getAllProjekatBodovanjeByProjekatId(@PathVariable Long id){
        return projekatBodovanjeRepository.findByProjekat_Id(id);
    }

    /**
     * GET  /projekat-bodovanjes/:id : get the "id" projekatBodovanje.
     *
     * @param id the id of the projekatBodovanje to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projekatBodovanje, or with status 404 (Not Found)
     */
    @GetMapping("/projekat-bodovanjes/{id}")
    @Timed
    public ResponseEntity<ProjekatBodovanje> getProjekatBodovanje(@PathVariable Long id) {
        log.debug("REST request to get ProjekatBodovanje : {}", id);
        Optional<ProjekatBodovanje> projekatBodovanje = projekatBodovanjeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projekatBodovanje);
    }

    /**
     * DELETE  /projekat-bodovanjes/:id : delete the "id" projekatBodovanje.
     *
     * @param id the id of the projekatBodovanje to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projekat-bodovanjes/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjekatBodovanje(@PathVariable Long id) {
        log.debug("REST request to delete ProjekatBodovanje : {}", id);

        projekatBodovanjeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
