package com.kursach.cinema.DAO;

import com.kursach.cinema.Model.Reservation;
import com.kursach.cinema.Model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SeatDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public SeatDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //return list of all seats in the cinema
    public List<Seat> indexSeat() {
        return jdbcTemplate.query("select * from seat", new BeanPropertyRowMapper<>(Seat.class));
    }

    //return list of all seats in the cinema
    public List<Seat> getSeatsInAuditorium(long auditoriumID) {
        String sql = "select * from seat where auditorium_id = ?";
        return jdbcTemplate.query(sql, new Object[]{auditoriumID}, new SeatRowMapper());
    }

    public List<Seat> getBusySeatsInScreening(long auditoriumID, long screeningID){
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);


        List<Seat> seats = getSeatsInAuditorium(auditoriumID);
        List<Reservation> reservations = reservationDAO.getAllReservations();

        List<Seat> reservedSeats = new ArrayList<>();

        for (Seat s : seats)
            for(Reservation res : reservations){
                if(res.getScreeningID() == screeningID && res.getSeatReservationID() == s.getId())
                    reservedSeats.add(s);
            }
        return reservedSeats;
    }

    public Seat getSeatIDForEachOtherParams(int number, int row, long auditoriumID){
        String sql = "select * from seat where row = ? and number = ? and auditorium_id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{row, number, auditoriumID}, new SeatRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

}

class SeatRowMapper implements RowMapper<Seat>{
    @Override
    public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Seat seat = new Seat();
        seat.setId(rs.getLong("id"));
        seat.setRow(rs.getInt("row"));
        seat.setNum(rs.getInt("number"));
        seat.setAuditoriumID(rs.getInt("auditorium_id"));


        return seat;
    }
}
