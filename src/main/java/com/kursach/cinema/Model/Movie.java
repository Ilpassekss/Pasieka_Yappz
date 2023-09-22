package com.kursach.cinema.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie {

    private long id;
    @NotEmpty(message = "Film name should not be empty")
    @Size(min = 2,  message = "Movie title should be more than 2 characters")
    private String title;
    @NotEmpty(message = "film director data should not be empty")
    @Size(min = 2,  message = "Movie director should be more than 2 characters")
    private String director;
    @NotEmpty(message = "Film cast can not be empty")
    @Size(min = 2,  message = "Movie cast description should be more than 2 characters")
    private String casting;
    @NotEmpty(message = "Description can not be empty")
    @Size(min = 2,  message = "Movie description should be more than 2 characters")
    private String description;
    @Min(value = 1, message = "Film duration can not be less than 1 minute")
    private int duration_min;


}
