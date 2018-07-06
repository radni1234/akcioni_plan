package com.plan.vr.repository;

import com.plan.vr.domain.Kriterijum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Kriterijum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KriterijumRepository extends JpaRepository<Kriterijum, Long> {

}
