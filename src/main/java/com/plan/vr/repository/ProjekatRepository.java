package com.plan.vr.repository;

import com.plan.vr.domain.Projekat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Projekat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjekatRepository extends JpaRepository<Projekat, Long> {

}
