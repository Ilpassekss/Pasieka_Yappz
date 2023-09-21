package com.kursach.cinema.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", screeningID=" + screeningID +
                ", employeeID=" + employeeID +
                ", paid=" + paid +
                ", seatReservationID=" + seatReservationID +
                ", active=" + active +
                ", reservationType='" + reservationType + '\'' +
                '}';
    }
}
