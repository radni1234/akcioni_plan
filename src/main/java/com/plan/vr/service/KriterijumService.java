package com.plan.vr.service;

import com.plan.vr.domain.Kriterijum;
import com.plan.vr.domain.Projekat;
import com.plan.vr.domain.ProjekatBodovanje;
import com.plan.vr.repository.KriterijumBodovanjeRepository;
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
    private final KriterijumBodovanjeRepository kriterijumBodovanjeRepository;
    private final ProjekatService projekatService;

    public KriterijumService(KriterijumRepository kriterijumRepository, ProjekatRepository projekatRepository, ProjekatBodovanjeRepository projekatBodovanjeRepository, KriterijumBodovanjeRepository kriterijumBodovanjeRepository, ProjekatService projekatService) {
        this.kriterijumRepository = kriterijumRepository;
        this.projekatRepository = projekatRepository;
        this.projekatBodovanjeRepository = projekatBodovanjeRepository;
        this.kriterijumBodovanjeRepository = kriterijumBodovanjeRepository;
        this.projekatService = projekatService;
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

        if (kriterijum.getId() == null) {

            List<Projekat> projekti = this.projekatRepository.findByAkcioniPlan_Id(k.getAkcioniPlan().getId());

            for (Projekat p : projekti) {
                ProjekatBodovanje pb = new ProjekatBodovanje();

                pb.projekat(p)
                    .kriterijum(k);

                this.projekatBodovanjeRepository.save(pb);
            }

        } else {
            List<ProjekatBodovanje> projekatBodovanjes = this.projekatBodovanjeRepository.findByKriterijum_Id(kriterijum.getId());

            for (ProjekatBodovanje pb : projekatBodovanjes) {
                pb.kriterijum(k);

                this.projekatBodovanjeRepository.save(pb);
            }
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

        List<ProjekatBodovanje> projekatBodovanjes = projekatBodovanjeRepository.findByKriterijum_Id(id);

        kriterijumBodovanjeRepository.deleteAll(kriterijumBodovanjeRepository.findAllByKriterijum_Id(id));
        projekatBodovanjeRepository.deleteAll(projekatBodovanjeRepository.findByKriterijum_Id(id));
        kriterijumRepository.deleteById(id);

        for (ProjekatBodovanje pb : projekatBodovanjes) {
            projekatService.preracunajUkupnoBodovi(pb.getProjekat().getId());
        }
    }

    public List<Kriterijum> findByAkcioniPlan_Id(Long id){
        return kriterijumRepository.findByAkcioniPlan_Id(id);
    }
}
