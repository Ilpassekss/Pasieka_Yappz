package com.kursach.cinema.DAO;

import com.kursach.cinema.Model.Movie;
import com.kursach.cinema.Model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ReservationDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservations(){

        String sql = "select * from reservation";

        return this.jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    public Reservation findReservationBySeatIDAndScreeningID(long seatID, long screeningID){

        String sql = "select * from reservation " +
                "where screaning_id = ? and seat_reservation_id = ?;";

        try {
            return this.jdbcTemplate.queryForObject(sql, new Object[]{screeningID, seatID}, new ReservationRowMapper());
        }catch (EmptyResultDataAccessException e){
            System.err.println(e.getCause());
            return null;
        }
    }

    public void addReservation(Reservation reservation) {

        String sql = "INSERT INTO reservation " +
                "(screaning_id, employee_id, reservation_type, paid, seat_reservation_id, active) " +
                "values (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, reservation.getScreeningID(), reservation.getEmployeeID(),
                reservation.getReservationType(), reservation.getPaid(),
                reservation.getSeatReservationID(), reservation.isActive());
    }

}

class ReservationRowMapper implements RowMapper<Reservation>{

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Reservation.builder()
                .id(rs.getLong("id"))
                .screeningID(rs.getLong("screaning_id"))
                .reservationType(rs.getString("reservation_type"))
                .paid(rs.getDouble("paid"))
                .seatReservationID(rs.getLong("seat_reservation_id"))
                .active(rs.getBoolean("active"))
                .employeeID(rs.getLong("employee_id"))
                .build();
    }
}