package com.kursach.cinema.Controllers;


import com.kursach.cinema.DAO.*;
import com.kursach.cinema.Model.Reservation;
import jakarta.validation.Valid;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/panel")
public class WorkerController {

    ScreeningDAO screeningDAO  ;
    MovieDAO movieDAO;
    AuditoriumDAO auditoriumDAO;
    SeatDAO seatDAO;
    ReservationDAO reservationDAO;
    Reservation reservation;


    @Autowired
    WorkerController(ScreeningDAO screeningDAO, MovieDAO movieDAO, AuditoriumDAO auditoriumDAO, SeatDAO seatDAO,
                     ReservationDAO reservationDAO){
        this.screeningDAO = screeningDAO;
        this.movieDAO = movieDAO;
        this.auditoriumDAO = auditoriumDAO;
        this.seatDAO = seatDAO;
        this.reservationDAO = reservationDAO;
    }


    @GetMapping()
    String getAllScreening(Model model){

        model.addAttribute("chooseScreenings", screeningDAO.indexScreenings());

        return "app/buyTicket/screenings";
    }

    @GetMapping("/{id}")
    String getRegistrationPage(@PathVariable("id") long screeningId,  Model model){

        model.addAttribute("screening", screeningDAO.getScreening(screeningId));
        model.addAttribute("film", movieDAO.getMovie(screeningDAO.getScreening(screeningId).getMovieID()));
        model.addAttribute("auditorium" , auditoriumDAO.getAuditorium(screeningDAO.getScreening(screeningId).getAuditoriumID()));

        model.addAttribute("seatsList", seatDAO.getSeatsInAuditorium(screeningDAO.getScreening(screeningId).getAuditoriumID()));
        model.addAttribute("busyPlaces", seatDAO.getBusySeatsInScreening(screeningDAO.getScreening(screeningId).getAuditoriumID(), screeningId));

        String str = "";

        model.addAttribute("reservations", new Reservation());
        model.addAttribute("reservationType", str);

        int num = 0;
        int row = 0;

        model.addAttribute("num", num);
        model.addAttribute("row", row);

        return "app/buyTicket/registrationPage";
    }

    //delete sout`s and use builder pattern
    @PostMapping("/{id}")
    String setPlaceBusy(@RequestParam("num") @Valid int number, @RequestParam("row") @Valid int row,
                        @PathVariable("id") int screeningID, @Valid String reservationType) {

        double payment = 0;

       if(reservationType.equals("STANDARD")){
           if(row == 1){
                payment = screeningDAO.getScreening(screeningID).getStandardPrice() * 1.1;
           } else if (row == 3) {
               payment = screeningDAO.getScreening(screeningID).getStandardPrice() * 0.9;
           }else {
               payment = screeningDAO.getScreening(screeningID).getStandardPrice();
           }

       }else{
            if(reservationType.equals("OLD"))
                payment = screeningDAO.getScreening(screeningID).getStandardPrice() * 0.8;
            else if (reservationType.equals("CHILD"))
                payment = screeningDAO.getScreening(screeningID).getStandardPrice() * 0.6;
       }

        if(reservationDAO.findReservationBySeatIDAndScreeningID(seatDAO.getSeatIDForEachOtherParams(number, row,
                screeningDAO.getScreening(screeningID).getAuditoriumID()).getId(), screeningID)==null){

            reservation = Reservation.builder()
                    .screeningID(screeningID)
                    .employeeID(2)
                    .reservationType(reservationType)
                    .paid(payment)
                    .seatReservationID(seatDAO
                            .getSeatIDForEachOtherParams(number, row, screeningDAO.getScreening(screeningID)
                                    .getAuditoriumID()).getId())
                    .active(true).build();

        }else {
            return "redirect:/panel/{id}";
        }
        return "redirect:/panel/finalResult";
    }

    @GetMapping("/finalResult")
    String showReservation(Model model){

        model.addAttribute("resultReservation", reservation);

        return "app/buyTicket/smthNew";
    }

    @PostMapping("/finalResults")
    String makeReservation(){

        reservationDAO.addReservation(reservation);

        return "redirect:/panel";
    }



}
