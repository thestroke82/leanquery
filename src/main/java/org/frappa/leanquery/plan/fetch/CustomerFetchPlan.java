package org.frappa.leanquery.plan.fetch;

import org.frappa.leanquery.plan.fetch.base.FetchPlan;
import org.frappa.leanquery.plan.fetch.base.FetchStrategy;

public class CustomerFetchPlan extends FetchPlan<CustomerFetchPlan.AllowedFetch> {

    private CustomerFetchPlan(){}
    public static CustomerFetchPlanBuilder builder(){
        return new CustomerFetchPlanBuilder();
    }
    public static class CustomerFetchPlanBuilder{
        private CustomerFetchPlan customerFetchPlan = new CustomerFetchPlan();

        public CustomerFetchPlanBuilder withRentals(){
            customerFetchPlan.selectedFetches.put(AllowedFetch.RENTALS, FetchStrategy.REPOSITORY);
            return this;
        }

        public CustomerFetchPlanBuilder withRentalsStaff(){
            customerFetchPlan.selectedFetches.put(AllowedFetch.RENTALS, FetchStrategy.REPOSITORY);
            RentalFetchPlan nestedPlan = RentalFetchPlan.builder().withStaff().build();
            customerFetchPlan.nestedFetchPlans.put(AllowedFetch.RENTALS, nestedPlan);
            return this;
        }
        public CustomerFetchPlanBuilder withRentalsStaffAndPayment(){
           customerFetchPlan.selectedFetches.put(AllowedFetch.RENTALS, FetchStrategy.REPOSITORY);
            RentalFetchPlan nestedPlan = RentalFetchPlan.builder().withStaff().withPayment().build();
            customerFetchPlan.nestedFetchPlans.put(AllowedFetch.RENTALS, nestedPlan);
            return this;
        }
        public CustomerFetchPlan build(){
            return customerFetchPlan;
        }


    }
    public enum AllowedFetch{
            RENTALS
        }
}
