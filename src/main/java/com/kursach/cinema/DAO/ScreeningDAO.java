package com.kursach.cinema.DAO;

import com.kursach.cinema.Model.Auditorium;
import com.kursach.cinema.Model.Movie;
import com.kursach.cinema.Model.Screening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class ScreeningDAO {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public ScreeningDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<Screening> indexScreenings() {

        MovieDAO movieDAO = new MovieDAO(jdbcTemplate);
        AuditoriumDAO auditoriumDAO = new AuditoriumDAO(jdbcTemplate);

        String sql = "select * from screaning";
        List<Screening> screeningList = jdbcTemplate.query(sql, new ScreeningRowMapper());

        List<Movie> mov = movieDAO.indexMovies();
        List<Auditorium> aud = auditoriumDAO.indexAuditoriums();

        for(Screening src : screeningList){
            for(Movie movie : mov){
                if(src.getMovieID() == movie.getId()){
                    src.setMovie(movie);
                }
            }
        }

        for(Screening src : screeningList){
            for(Auditorium auditorium : aud){
                if(src.getAuditoriumID() == auditorium.getId()){
                    src.setAuditorium(auditorium);
                }
            }
        }
        return screeningList;
    }

    //add new screening into db
    public void addScreening(Screening screening) {
        String sql = "INSERT INTO public.screaning( movie_id, auditorium_id, screaning_date, start_time, " +
                "standard_price) VALUES ( ?, ?, cast(? as date), cast(? as time without time zone), ?)";
        jdbcTemplate.update(sql, screening.getMovieID(), screening.getAuditoriumID(), screening.getScreening_date(),
                screening.getStart_time(), screening.getStandardPrice());
    }


    public Screening getScreening(long id){
        String sql ="select * from screaning where id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new ScreeningRowMapper()).stream().findAny().orElse(null);
    }

    public void updateScreening(Screening screening){
        String sql = "update screaning set screaning_date = cast( ? as date) , start_time = cast( ? as time without time zone) , standard_price = ? where id=?";
        jdbcTemplate.update(sql, screening.getScreening_date(), screening.getStart_time(), screening.getStandardPrice(), screening.getId());
    }

    public void deleteScreening(long screeningID){
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, screeningID);
    }
}

class ScreeningRowMapper implements RowMapper<Screening>{

    @Override
    public Screening mapRow(ResultSet rs, int rowNum) throws SQLException {
        Screening screening = new Screening();

        screening.setId(rs.getLong("id"));
        screening.setMovieID(rs.getLong("movie_id"));
        screening.setAuditoriumID(rs.getLong("auditorium_id"));
        screening.setScreening_date(rs.getString("screaning_date"));
        screening.setStart_time(rs.getString("start_time"));
        screening.setStandardPrice(rs.getLong("standard_price"));

        return screening;
    }
}