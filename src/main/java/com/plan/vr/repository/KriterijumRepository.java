package com.plan.vr.repository;

import com.plan.vr.domain.Kriterijum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Kriterijum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KriterijumRepository extends JpaRepository<Kriterijum, Long> {
    List<Kriterijum> findByAkcioniPlan_Id(Long id);
}
