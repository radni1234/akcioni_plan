package com.plan.vr.repository;

import com.plan.vr.domain.ProjekatBodovanje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjekatBodovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjekatBodovanjeRepository extends JpaRepository<ProjekatBodovanje, Long> {

}
