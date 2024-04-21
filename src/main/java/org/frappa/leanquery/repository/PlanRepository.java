package org.frappa.leanquery.repository;

import java.util.List;

public interface PlanRepository <R, C, F >{
    List<R> findByPlan(C filterPlan, F fetchPlan);
}
