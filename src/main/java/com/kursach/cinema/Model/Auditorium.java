package com.kursach.cinema.Model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Auditorium {
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private int seatsNumber;

}
