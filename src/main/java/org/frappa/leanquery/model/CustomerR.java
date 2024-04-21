package org.frappa.leanquery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@Getter
@Setter
public class CustomerR {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private List<RentalR> rentals;
    private List<Payment> payments;


    @Builder
    @Getter
    @Setter
    public static class Payment{
        private Integer id;
        private BigDecimal amount;
        private Instant paymentDate;
    }
}
