package org.frappa.leanquery.model.mapper;

import org.frappa.leanquery.model.CustomerQ;
import org.frappa.leanquery.model.CustomerR;
import org.frappa.leanquery.plan.filter.CustomerFilterPlan;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.frappa.leanquery.jooq.tables.Customer.CUSTOMER;
import static org.frappa.leanquery.jooq.tables.Film.FILM;
import static org.frappa.leanquery.jooq.tables.Payment.PAYMENT;
import static org.frappa.leanquery.jooq.tables.Rental.RENTAL;
import static org.frappa.leanquery.jooq.tables.Staff.STAFF;

@Component
public class CustomerMapper {

    public CustomerR mapCustomer(Record input){
        if(input==null){
            return null;
        }
        CustomerR output= CustomerR.builder()
                .id(input.get(CUSTOMER.CUSTOMER_ID))
                .firstName(input.get(CUSTOMER.FIRST_NAME))
                .lastName(input.get(CUSTOMER.LAST_NAME))
                .email(input.get(CUSTOMER.EMAIL))
                .payments(new ArrayList<>())
                .rentals(new ArrayList<>())
                .build();
        return output;
    }

    public CustomerR.Payment mapPayment(Record input){
        if(input==null){
            return null;
        }
        CustomerR.Payment output= CustomerR.Payment.builder()
                .id(input.get(PAYMENT.PAYMENT_ID))
                .amount(input.get(PAYMENT.AMOUNT))
                .paymentDate(input.get(PAYMENT.PAYMENT_DATE))
                .build();
        return output;
    }


    public CustomerFilterPlan mapCustomerFilterPlan(CustomerQ input){
        if(input==null){
            return null;
        }
        CustomerFilterPlan output= CustomerFilterPlan.builder()
                .idIn(input.getIds())
                .firstNameLike(input.getFirstName())
                .lastNameLike(input.getLastName())
                .rentedMovieTitleLike(input.getRentedMovieTitle())
                .build();
        return output;
    }
}
