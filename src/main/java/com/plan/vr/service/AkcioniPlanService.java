package com.plan.vr.service;

import com.plan.vr.domain.*;
import com.plan.vr.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing AkcioniPlan.
 */
@Service
@Transactional
public class AkcioniPlanService {

    private final Logger log = LoggerFactory.getLogger(AkcioniPlanService.class);

    private final AkcioniPlanRepository akcioniPlanRepository;
    private final AdminKriterijumRepository adminKriterijumRepository;
    private final AdminKriterijumBodovanjeRepository adminKriterijumBodovanjeRepository;
    private final KriterijumRepository kriterijumRepository;
    private final KriterijumBodovanjeRepository kriterijumBodovanjeRepository;
    private final ProjekatRepository projekatRepository;
    private final UserService userService;
    private final ProjekatService projekatService;
    private final KriterijumService kriterijumService;

    public AkcioniPlanService(AkcioniPlanRepository akcioniPlanRepository, AdminKriterijumRepository adminKriterijumRepository, AdminKriterijumBodovanjeRepository adminKriterijumBodovanjeRepository, KriterijumRepository kriterijumRepository, KriterijumBodovanjeRepository kriterijumBodovanjeRepository, ProjekatRepository projekatRepository, UserService userService, ProjekatService projekatService, KriterijumService kriterijumService) {
        this.akcioniPlanRepository = akcioniPlanRepository;
        this.adminKriterijumRepository = adminKriterijumRepository;
        this.adminKriterijumBodovanjeRepository = adminKriterijumBodovanjeRepository;
        this.kriterijumRepository = kriterijumRepository;
        this.kriterijumBodovanjeRepository = kriterijumBodovanjeRepository;
        this.projekatRepository = projekatRepository;
        this.userService = userService;
        this.projekatService = projekatService;
        this.kriterijumService = kriterijumService;
    }

    /**
     * Save a akcioniPlan.
     *
     * @param akcioniPlan the entity to save
     * @return the persisted entity
     */
    public AkcioniPlan save(AkcioniPlan akcioniPlan) {
        log.debug("Request to save AkcioniPlan : {}", akcioniPlan);

        final Optional<User> isUser = userService.getUserWithAuthorities();
        if(!isUser.isPresent()) {
            log.error("User is not logged in");
        } else {
            akcioniPlan.setUser(isUser.get());
            log.debug("Korisnik je : {}", isUser.get());
        }

        if (akcioniPlan.getId() == null ) {
            AkcioniPlan ap = akcioniPlanRepository.save(akcioniPlan);

            List<AdminKriterijum> kriterijumi = adminKriterijumRepository.findAll();

            for (AdminKriterijum ak : kriterijumi) {

                List<AdminKriterijumBodovanje> kriterijumBodovanje = adminKriterijumBodovanjeRepository.findAllByAdminKriterijum_id(ak.getId());

                Kriterijum k = new Kriterijum();
                k.kriterijumTip(ak.getKriterijumTip())
                    .naziv(ak.getNaziv())
                    .ponder(ak.getPonder())
                    .akcioniPlan(ap);

                Kriterijum k2 = kriterijumRepository.save(k);

                for (AdminKriterijumBodovanje akb : kriterijumBodovanje) {

                    KriterijumBodovanje kb = new KriterijumBodovanje();
                    kb.rb(akb.getRb())
                        .granica(akb.getGranica())
                        .opis(akb.getOpis())
                        .bodovi(akb.getBodovi())
                        .kriterijum(k2);

                    kriterijumBodovanjeRepository.save(kb);
                }

            }

            return ap;
        } else {
            return akcioniPlanRepository.save(akcioniPlan);
        }
    }

    /**
     * Get all the akcioniPlans.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AkcioniPlan> findAll() {
        log.debug("Request to get all AkcioniPlans");
        return akcioniPlanRepository.findByUserIsCurrentUser();
    }


    /**
     * Get one akcioniPlan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AkcioniPlan> findOne(Long id) {
        log.debug("Request to get AkcioniPlan : {}", id);
        return akcioniPlanRepository.findById(id);
    }

    /**
     * Delete the akcioniPlan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AkcioniPlan : {}", id);
        List<Kriterijum> kriterijumi = kriterijumService.findByAkcioniPlan_Id(id);

        for (Kriterijum k : kriterijumi) {
            kriterijumService.delete(k.getId());
        }

        List<Projekat> projekti = projekatService.findByAkcioniPlan_Id(id);

        for (Projekat p : projekti) {
            projekatService.delete(p.getId());
        }

        akcioniPlanRepository.deleteById(id);
    }
}
