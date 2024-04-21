package org.frappa.leanquery.plan.fetch;

public class CustomerFetchPlan extends FetchPlan{

    private CustomerFetchPlan(){}
    public static CustomerFetchPlanBuilder builder(){
        return new CustomerFetchPlanBuilder();
    }
    public static class CustomerFetchPlanBuilder{
        private CustomerFetchPlan customerFetchPlan = new CustomerFetchPlan();

        public CustomerFetchPlanBuilder withRentals(){
            customerFetchPlan.selectedFetches.add(Fetch.rental);
            return this;
        }
        public CustomerFetchPlanBuilder withPayments(){
            customerFetchPlan.selectedFetches.add(Fetch.payment);
            return this;
        }
        public CustomerFetchPlan build(){
            return customerFetchPlan;
        }
    }
}
