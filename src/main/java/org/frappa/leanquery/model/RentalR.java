package org.frappa.leanquery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private Film film;

    @Builder
    @Getter
    @Setter
    public static class Film{
        private Integer id;
        private String title;
        private String description;
        private Integer releaseYear;
    }

    @Builder
    @Getter
    @Setter
    public static class Staff{
        private Integer id;
        private String  firstName;
        private String  lastName;
    }
}