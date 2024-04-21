package org.frappa.leanquery.plan.fetch;

import org.frappa.leanquery.plan.fetch.base.FetchPlan;
import org.frappa.leanquery.plan.fetch.base.FetchStrategy;

public class RentalFetchPlan extends FetchPlan<RentalFetchPlan.AllowedFetch> {

    // convenience method if we want to check if the staff is fetched via a join
    public boolean isJoinStaff(){
        return this.selectedFetches!=null && this.selectedFetches.get(AllowedFetch.STAFF) == FetchStrategy.JOIN;
    }

    // convenience method if we want to check if the payment is fetched via a join
    public boolean isJoinPayment(){
        return this.selectedFetches!=null && this.selectedFetches.get(AllowedFetch.PAYMENT) == FetchStrategy.JOIN;
    }

    // convenience method if we want to check if the film is fetched via a join
    public boolean isJoinFilm(){
        return this.selectedFetches!=null && this.selectedFetches.get(AllowedFetch.FILM) == FetchStrategy.JOIN;
    }

    public static RentalFetchPlanBuilder builder(){
        return new RentalFetchPlanBuilder();
    }
    public static class RentalFetchPlanBuilder{
        private RentalFetchPlan rentalFetchPlan = new RentalFetchPlan();

        public RentalFetchPlanBuilder withStaff(){
            this.rentalFetchPlan.selectedFetches.put(AllowedFetch.STAFF, FetchStrategy.JOIN);
            return this;
        }
        public RentalFetchPlanBuilder withPayment(){
            this.rentalFetchPlan.selectedFetches.put(AllowedFetch.PAYMENT, FetchStrategy.JOIN);
            return this;
        }

         public RentalFetchPlanBuilder withFilm(){
            this.rentalFetchPlan.selectedFetches.put(AllowedFetch.FILM, FetchStrategy.JOIN);
            return this;
        }
        public RentalFetchPlan build(){
            return rentalFetchPlan;
        }

    }
    public enum AllowedFetch{
            STAFF, PAYMENT, FILM
        }
}
