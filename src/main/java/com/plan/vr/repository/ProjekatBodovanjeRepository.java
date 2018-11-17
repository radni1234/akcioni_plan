package com.plan.vr.repository;

import com.plan.vr.domain.ProjekatBodovanje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ProjekatBodovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjekatBodovanjeRepository extends JpaRepository<ProjekatBodovanje, Long> {
    List<ProjekatBodovanje> findByProjekat_Id(Long id);
    List<ProjekatBodovanje> findByKriterijum_Id(Long id);
}
