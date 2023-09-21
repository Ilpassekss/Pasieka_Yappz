package com.kursach.cinema.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatReservation {

    private Seat seat;
    private boolean busy;
    private Screening screening;
}
