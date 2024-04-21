package org.frappa.leanquery.plan.fetch;

import org.frappa.leanquery.plan.fetch.base.FetchPlan;
import org.frappa.leanquery.plan.fetch.base.FetchStrategy;

public class FilmFetchPlan extends FetchPlan<FilmFetchPlan.AllowedFetch> {

    public boolean isJoinLanguage(){
        return this.selectedFetches!=null && this.selectedFetches.get(AllowedFetch.LANGUAGE) == FetchStrategy.JOIN;
    }


    public static FilmFetchPlanBuilder builder(){
        return new FilmFetchPlanBuilder();
    }
    public static class FilmFetchPlanBuilder{
        private FilmFetchPlan filmFetchPlan = new FilmFetchPlan();

        public FilmFetchPlanBuilder withLanguage(){
            this.filmFetchPlan.selectedFetches.put(AllowedFetch.LANGUAGE, FetchStrategy.JOIN);
            return this;
        }

        public FilmFetchPlan build(){
            return filmFetchPlan;
        }
    }
    public enum AllowedFetch{
        LANGUAGE
    }
}
