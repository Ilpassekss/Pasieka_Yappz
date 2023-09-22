package com.kursach.cinema.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Seat {


    private long id;
    private int row;
    private int num;
    private Auditorium auditorium;
    private long auditoriumID;

    public Seat(int row, int num, Auditorium auditorium) {
        this.row = row;
        this.num = num;
        this.auditorium = auditorium;
    }

}
