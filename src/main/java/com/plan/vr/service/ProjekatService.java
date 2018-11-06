package com.plan.vr.service;

import com.plan.vr.domain.Kriterijum;
import com.plan.vr.domain.Projekat;
import com.plan.vr.domain.ProjekatBodovanje;
import com.plan.vr.repository.KriterijumRepository;
import com.plan.vr.repository.ProjekatBodovanjeRepository;
import com.plan.vr.repository.ProjekatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Projekat.
 */
@Service
@Transactional
public class ProjekatService {

    private final Logger log = LoggerFactory.getLogger(ProjekatService.class);

    private final ProjekatRepository projekatRepository;
    private final KriterijumRepository kriterijumRepository;
    private final ProjekatBodovanjeRepository projekatBodovanjeRepository;


    public ProjekatService(ProjekatRepository projekatRepository, KriterijumRepository kriterijumRepository, ProjekatBodovanjeRepository projekatBodovanjeRepository) {
        this.projekatRepository = projekatRepository;
        this.kriterijumRepository = kriterijumRepository;
        this.projekatBodovanjeRepository = projekatBodovanjeRepository;
    }

    /**
     * Save a projekat.
     *
     * @param projekat the entity to save
     * @return the persisted entity
     */
    public Projekat save(Projekat projekat) {
        log.debug("Request to save Projekat : {}", projekat);

        Projekat p = projekatRepository.save(projekat);

        List<Kriterijum> kriterijumi = kriterijumRepository.findByAkcioniPlan_Id(p.getAkcioniPlan().getId());

        for (Kriterijum k : kriterijumi) {

            ProjekatBodovanje pb = new ProjekatBodovanje();

            pb.projekat(p)
                .kriterijum(k);

            projekatBodovanjeRepository.save(pb);
        }

        return p;
    }

    /**
     * Get all the projekats.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Projekat> findAll() {
        log.debug("Request to get all Projekats");
        return projekatRepository.findAll();
    }


    /**
     * Get one projekat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Projekat> findOne(Long id) {
        log.debug("Request to get Projekat : {}", id);
        return projekatRepository.findById(id);
    }

    /**
     * Delete the projekat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Projekat : {}", id);
        projekatRepository.deleteById(id);
    }

    public List<Projekat> findByAkcioniPlan_Id(Long id){
        return projekatRepository.findByAkcioniPlan_Id(id);
    }
}
