package org.frappa.leanquery.model.mapper;

import org.frappa.leanquery.model.FilmR;
import org.frappa.leanquery.model.RentalQ;
import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.plan.filter.RentalFilterPlan;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static org.frappa.leanquery.jooq.tables.Film.FILM;
import static org.frappa.leanquery.jooq.tables.Payment.PAYMENT;
import static org.frappa.leanquery.jooq.tables.Rental.RENTAL;
import static org.frappa.leanquery.jooq.tables.Staff.STAFF;

@Component
public class RentalMapper {

    public RentalR mapRental(Record input){
        if(input==null){
            return null;
        }
        RentalR output= RentalR.builder()
                .id(input.get(RENTAL.RENTAL_ID))
                .customerId(input.get(RENTAL.CUSTOMER_ID))
                .rentalDate(input.get(RENTAL.RENTAL_DATE))
                .returnDate(input.get(RENTAL.RETURN_DATE))
                .build();
        return output;
    }


    public RentalR.Staff mapStaff(Record input){
        if(input==null){
            return null;
        }
       RentalR.Staff output= RentalR.Staff.builder()
                .id(input.get(STAFF.STAFF_ID))
                .firstName(input.get(STAFF.FIRST_NAME))
                .lastName(input.get(STAFF.LAST_NAME))
                .build();
        return output;
    }

    public RentalR.Payment mapPayment(Record input){
        if(input==null){
            return null;
        }
        RentalR.Payment output= RentalR.Payment.builder()
                .id(input.get(PAYMENT.PAYMENT_ID))
                .amount(input.get(PAYMENT.AMOUNT))
                .paymentDate(input.get(PAYMENT.PAYMENT_DATE))
                .build();
        return output;
    }

    public RentalFilterPlan mapRentalFilterPlan(RentalQ input){
        if(input==null){
            return null;
        }
        RentalFilterPlan output= RentalFilterPlan.builder()
                .customerIdIn(input.getCustomerIds())
                .build();
        return output;
    }
}
