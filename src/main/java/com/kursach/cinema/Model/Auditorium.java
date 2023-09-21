package com.kursach.cinema.Model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Auditorium {
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private int seatsNumber;


    @Override
    public String toString() {
        return "Auditorium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seats_number=" + seatsNumber +
                '}';
    }
}
