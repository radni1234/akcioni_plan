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
 * Service Implementation for managing Kriterijum.
 */
@Service
@Transactional
public class KriterijumService {

    private final Logger log = LoggerFactory.getLogger(KriterijumService.class);

    private final KriterijumRepository kriterijumRepository;
    private final ProjekatRepository projekatRepository;
    private final ProjekatBodovanjeRepository projekatBodovanjeRepository;

    public KriterijumService(KriterijumRepository kriterijumRepository, ProjekatRepository projekatRepository, ProjekatBodovanjeRepository projekatBodovanjeRepository) {
        this.kriterijumRepository = kriterijumRepository;
        this.projekatRepository = projekatRepository;
        this.projekatBodovanjeRepository = projekatBodovanjeRepository;
    }

    /**
     * Save a kriterijum.
     *
     * @param kriterijum the entity to save
     * @return the persisted entity
     */
    public Kriterijum save(Kriterijum kriterijum) {
        log.debug("Request to save Kriterijum : {}", kriterijum);
        Kriterijum k = kriterijumRepository.save(kriterijum);

        List<Projekat> projekti = this.projekatRepository.findByAkcioniPlan_Id(k.getAkcioniPlan().getId());

        for (Projekat p : projekti) {
            ProjekatBodovanje pb = new ProjekatBodovanje();

            pb.projekat(p)
                .kriterijum(k);

            this.projekatBodovanjeRepository.save(pb);
        }

        return k;
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
