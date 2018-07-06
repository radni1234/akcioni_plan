package com.plan.vr.repository;

import com.plan.vr.domain.AkcioniPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AkcioniPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkcioniPlanRepository extends JpaRepository<AkcioniPlan, Long> {

    @Query("select akcioni_plan from AkcioniPlan akcioni_plan where akcioni_plan.user.login = ?#{principal.username}")
    List<AkcioniPlan> findByUserIsCurrentUser();

}
