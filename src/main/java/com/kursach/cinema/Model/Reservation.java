package com.kursach.cinema.Model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Reservation {

    private ArrayList<String> reservationTypes = new ArrayList<>();

    //different id`s
    private long id;
    private long screeningID;
    private long employeeID;
    private double paid;
    private long seatReservationID;
    private boolean active;
    private String reservationType;

    public Reservation(){
        reservationTypes.add("STANDARD");
        reservationTypes.add("CHILD");
        reservationTypes.add("OLD");
    }

    public Reservation(long id, long screeningID, long employeeID, double paid, long seatReservationID, boolean active,
                       String reservationType) {
        this.id = id;
        this.screeningID = screeningID;
        this.employeeID = employeeID;
        this.paid = paid;
        this.seatReservationID = seatReservationID;
        this.active = active;
        this.reservationType = reservationType;
        reservationTypes.add("STANDARD");
        reservationTypes.add("CHILD");
        reservationTypes.add("OLD");
    }

}
