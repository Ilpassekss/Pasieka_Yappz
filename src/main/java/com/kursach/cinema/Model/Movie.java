package com.kursach.cinema.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public Movie(){

    }

    public Movie(String title, String director, String casting, String description, int duration_min) {
        this.title = title;
        this.director = director;
        this.casting = casting;
        this.description = description;
        this.duration_min = duration_min;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", cast='" + casting + '\'' +
                ", description='" + description + '\'' +
                ", filmDuration=" + duration_min +
                '}';
    }
}
