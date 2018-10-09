package com.plan.vr.service;

import com.plan.vr.domain.Kriterijum;
import com.plan.vr.repository.KriterijumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Kriterijum.
 */
@Service
@Transactional
public class KriterijumService {

    private final Logger log = LoggerFactory.getLogger(KriterijumService.class);

    private final KriterijumRepository kriterijumRepository;

    public KriterijumService(KriterijumRepository kriterijumRepository) {
        this.kriterijumRepository = kriterijumRepository;
    }

    /**
     * Save a kriterijum.
     *
     * @param kriterijum the entity to save
     * @return the persisted entity
     */
    public Kriterijum save(Kriterijum kriterijum) {
        log.debug("Request to save Kriterijum : {}", kriterijum);        return kriterijumRepository.save(kriterijum);
    }

    /**
     * Get all the kriterijums.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Kriterijum> findAll() {
        log.debug("Request to get all Kriterijums");
        return kriterijumRepository.findAll();
    }


    /**
     * Get one kriterijum by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Kriterijum> findOne(Long id) {
        log.debug("Request to get Kriterijum : {}", id);
        return kriterijumRepository.findById(id);
    }

    /**
     * Delete the kriterijum by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Kriterijum : {}", id);
        kriterijumRepository.deleteById(id);
    }

    public List<Kriterijum> findByAkcioniPlan_Id(Long id){
        return kriterijumRepository.findByAkcioniPlan_Id(id);
    }
}
