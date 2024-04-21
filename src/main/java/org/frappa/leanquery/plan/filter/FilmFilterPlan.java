package org.frappa.leanquery.plan.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.frappa.leanquery.plan.filter.base.FilterPlan;

@Builder
@Getter
@Setter
public class FilmFilterPlan implements FilterPlan {
    private String titleLike;
}
