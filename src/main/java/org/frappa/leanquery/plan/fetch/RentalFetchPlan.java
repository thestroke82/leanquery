package org.frappa.leanquery.plan.fetch;

import org.frappa.leanquery.plan.fetch.base.Fetch;
import org.frappa.leanquery.plan.fetch.base.FetchPlan;
import org.frappa.leanquery.plan.fetch.base.FetchStrategy;

public class RentalFetchPlan extends FetchPlan {

    // convenience method if we want to check if the staff is fetched via a join
    public boolean isJoinStaff(){
        return this.selectedFetches!=null && this.selectedFetches.get(Fetch.STAFF) == FetchStrategy.JOIN;
    }
    public static RentalFetchPlanBuilder builder(){
        return new RentalFetchPlanBuilder();
    }
    public static class RentalFetchPlanBuilder{
        private RentalFetchPlan rentalFetchPlan = new RentalFetchPlan();

        public RentalFetchPlanBuilder withStaff(){
            this.rentalFetchPlan.selectedFetches.put(Fetch.STAFF, FetchStrategy.JOIN);
            return this;
        }
        public RentalFetchPlan build(){
            return rentalFetchPlan;
        }
    }
}
