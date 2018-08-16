package com.plan.vr.repository;

import com.plan.vr.domain.AdminKriterijum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdminKriterijum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminKriterijumRepository extends JpaRepository<AdminKriterijum, Long> {

}
