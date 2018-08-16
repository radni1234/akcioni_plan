package com.plan.vr.repository;

import com.plan.vr.domain.AdminKriterijumBodovanje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdminKriterijumBodovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminKriterijumBodovanjeRepository extends JpaRepository<AdminKriterijumBodovanje, Long> {

}
