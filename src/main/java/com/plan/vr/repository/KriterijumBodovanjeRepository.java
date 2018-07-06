package com.plan.vr.repository;

import com.plan.vr.domain.KriterijumBodovanje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the KriterijumBodovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KriterijumBodovanjeRepository extends JpaRepository<KriterijumBodovanje, Long> {

}
