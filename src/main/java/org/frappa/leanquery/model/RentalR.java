package org.frappa.leanquery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@Setter
public class RentalR {
    private Integer id;
    private Integer customerId;
    private Instant rentalDate;
    private Instant returnDate;
    private Staff staff;
    private Payment payment;
    private FilmR film;


    @Builder
    @Getter
    @Setter
    public static class Staff{
        private Integer id;
        private String  firstName;
        private String  lastName;
    }


    @Builder
    @Getter
    @Setter
    public static class Payment{
        private Integer id;
        private BigDecimal amount;
        private Instant paymentDate;
    }
}