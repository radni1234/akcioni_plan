package com.plan.vr.repository;

import com.plan.vr.domain.AdminKriterijumBodovanje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AdminKriterijumBodovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminKriterijumBodovanjeRepository extends JpaRepository<AdminKriterijumBodovanje, Long> {
     List<AdminKriterijumBodovanje> findAllByAdminKriterijum_id(Long id);
}
