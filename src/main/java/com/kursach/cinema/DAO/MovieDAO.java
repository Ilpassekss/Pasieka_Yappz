package com.kursach.cinema.DAO;

import com.kursach.cinema.Model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class MovieDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Movie> indexMovies() {
        return jdbcTemplate.query("select * from movie", new BeanPropertyRowMapper<>(Movie.class));
    }

    //add new movie into list
    public void addMovie(Movie movie) {
        jdbcTemplate.update("INSERT INTO movie (title, director, casting, description, duration_min) values (?, ?, ?, ?, ?)",
                movie.getTitle(), movie.getDirector(), movie.getCasting(), movie.getDescription(), movie.getDuration_min());
    }

    public Movie getMovie(long id){
        String sql = "select * from movie where id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Movie.class)).stream().findAny().orElse(null);
    }

    public void updateMovie(Movie updatedMovie){
        String sql = "update movie set title = ? , director =? , casting = ?, description = ?, duration_min = ?  where id=?";
        jdbcTemplate.update(sql, updatedMovie.getTitle(), updatedMovie.getDirector(), updatedMovie.getCasting(), updatedMovie.getDescription(),
                updatedMovie.getDuration_min(), updatedMovie.getId());
    }
    public void deleteMovie(long movieID){
        String sql = "delete from movie where id = ?";
        jdbcTemplate.update(sql, movieID);
    }
}

//class MovieMapper implements RowMapper<Movie>{
//
//    @Override
//    public Movie mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//        Movie movie = new Movie();
//        movie.setId(resultSet.getLong("id"));
//        movie.setTitle(resultSet.getString("title"));
//        movie.setDirector(resultSet.getString("director"));
//        movie.setCast(resultSet.getString("cast"));
//        movie.setDescription(resultSet.getString("description"));
//        movie.setFilmDuration(resultSet.getInt("duration_min"));
//        return movie;
//    }
//}