package org.frappa.leanquery.plan.fetch;

import org.frappa.leanquery.plan.fetch.base.Fetch;
import org.frappa.leanquery.plan.fetch.base.FetchPlan;
import org.frappa.leanquery.plan.fetch.base.FetchStrategy;

public class CustomerFetchPlan extends FetchPlan {

    private CustomerFetchPlan(){}
    public static CustomerFetchPlanBuilder builder(){
        return new CustomerFetchPlanBuilder();
    }
    public static class CustomerFetchPlanBuilder{
        private CustomerFetchPlan customerFetchPlan = new CustomerFetchPlan();

        public CustomerFetchPlanBuilder withRentals(){
            customerFetchPlan.selectedFetches.put(Fetch.RENTAL, FetchStrategy.REPOSITORY);
            return this;
        }
        public CustomerFetchPlanBuilder withPayments(){
            customerFetchPlan.selectedFetches.put(Fetch.PAYMENT, FetchStrategy.REPOSITORY);
            return this;
        }
        public CustomerFetchPlan build(){
            return customerFetchPlan;
        }
    }
}
