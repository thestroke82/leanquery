package org.frappa.leanquery.plan.fetch;

public class RentalFetchPlan extends FetchPlan{

    public static RentalFetchPlanBuilder builder(){
        return new RentalFetchPlanBuilder();
    }
    public static class RentalFetchPlanBuilder{
        private RentalFetchPlan rentalFetchPlan = new RentalFetchPlan();

        public RentalFetchPlan build(){
            return rentalFetchPlan;
        }
    }
}
