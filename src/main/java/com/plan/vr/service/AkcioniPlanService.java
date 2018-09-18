package com.plan.vr.service;

import com.plan.vr.domain.AdminKriterijum;
import com.plan.vr.domain.AkcioniPlan;
import com.plan.vr.domain.Kriterijum;
import com.plan.vr.domain.User;
import com.plan.vr.repository.AdminKriterijumRepository;
import com.plan.vr.repository.AkcioniPlanRepository;
import com.plan.vr.repository.KriterijumRepository;
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
    private final KriterijumRepository kriterijumRepository;
    private final UserService userService;

    public AkcioniPlanService(AkcioniPlanRepository akcioniPlanRepository, AdminKriterijumRepository adminKriterijumRepository, KriterijumRepository kriterijumRepository, UserService userService) {
        this.akcioniPlanRepository = akcioniPlanRepository;
        this.adminKriterijumRepository = adminKriterijumRepository;
        this.kriterijumRepository = kriterijumRepository;
        this.userService = userService;
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

        AkcioniPlan ap = akcioniPlanRepository.save(akcioniPlan);

        List<AdminKriterijum> kriterijumi = adminKriterijumRepository.findAll();

        for (AdminKriterijum ak : kriterijumi) {

            Kriterijum k = new Kriterijum();
            k.kriterijumTip(ak.getKriterijumTip())
                .naziv(ak.getNaziv())
                .ponder(ak.getPonder())
                .akcioniPlan(ap);

            kriterijumRepository.save(k);
        }

        System.out.println(akcioniPlan);

        return ap;
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
        akcioniPlanRepository.deleteById(id);
    }
}
