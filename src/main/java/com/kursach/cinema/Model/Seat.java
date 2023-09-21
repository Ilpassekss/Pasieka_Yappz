package com.kursach.cinema.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public Seat(){}

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", row=" + row +
                ", num=" + num +
                ", auditorium=" + auditorium +
                ", auditoriumID=" + auditoriumID +
                '}';
    }
}
