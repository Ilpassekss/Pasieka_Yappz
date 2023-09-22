package com.kursach.cinema.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Screening {

    private long id;

    private long movieID;
    private long auditoriumID;
    private Movie movie;
    private Auditorium auditorium;
    @NotEmpty(message = "Screening date should not be empty")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date should be in the format YYYY-MM-DD")
    private String screening_date;
    @NotEmpty(message = "Screening start time should not be empty")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$", message = "Time should be in the format HH:MM:SS")
    private String start_time;
    @Min(value = 1, message = "price can not be less than 1 ")
    private double standardPrice;

    public Screening(Movie movie, Auditorium auditorium,
                     String screening_date, String start_time, double standardPrice) {
        this.screening_date = screening_date;
        this.start_time = start_time;
        this.standardPrice = standardPrice;
    }

}
