package com.kursach.cinema.Controllers;


import com.kursach.cinema.DAO.*;
import com.kursach.cinema.Model.Reservation;
import com.kursach.cinema.Model.Screening;
import com.kursach.cinema.Model.Seat;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Indexed;
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
        //System.out.println(screeningDAO.getScreening(1).toString());
        return "app/buyTicket/screenings";
    }

    @GetMapping("/{id}")
    String getRegistrationPage(@PathVariable("id") long id,  Model model){

        model.addAttribute("screening", screeningDAO.getScreening(id));
        model.addAttribute("film", movieDAO.getMovie(screeningDAO.getScreening(id).getMovieID()));
        model.addAttribute("auditorium" , auditoriumDAO.getAuditorium(screeningDAO.getScreening(id).getAuditoriumID()));

        model.addAttribute("seatsList", seatDAO.getSeatsInAuditorium(screeningDAO.getScreening(id).getAuditoriumID()));
        model.addAttribute("busyPlaces", seatDAO.getBusySeatsInScreening(screeningDAO.getScreening(id).getAuditoriumID(), id));

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
    String setPlaceBusy(@RequestParam("num") @Valid int number, @RequestParam("row") @Valid int row, @PathVariable("id") int ScreeningID,
                        @Valid String reservationType) {



        System.out.println("Seat num: "+number + " " + "Row: "+ row + " " + screeningDAO.getScreening(ScreeningID).getAuditoriumID());
        // screening id
        System.out.println("Screening: "+ScreeningID);

        System.out.println(seatDAO.getSeatIDForEachOtherParams(number, row, screeningDAO.getScreening(ScreeningID).getAuditoriumID()).toString());
        System.out.println(reservationDAO.findReservationBySeatIDAndScreeningID(seatDAO.getSeatIDForEachOtherParams(number,
                row, screeningDAO.getScreening(ScreeningID).getAuditoriumID()).getId(), ScreeningID));

        System.out.println("reservation type: "+ reservationType);

        
        double payment = 0;

       if(reservationType.equals("STANDARD")){
           if(row == 1){
                payment = screeningDAO.getScreening(ScreeningID).getStandardPrice() * 1.1;
           } else if (row == 3) {
               payment = screeningDAO.getScreening(ScreeningID).getStandardPrice() * 0.9;
           }else {
               payment = screeningDAO.getScreening(ScreeningID).getStandardPrice();
           }

       }else{
            if(reservationType.equals("OLD"))
                payment = screeningDAO.getScreening(ScreeningID).getStandardPrice() * 0.8;
            else if (reservationType.equals("CHILD"))
                payment = screeningDAO.getScreening(ScreeningID).getStandardPrice() * 0.6;
       }

        System.out.println(payment);

        if(reservationDAO.findReservationBySeatIDAndScreeningID(seatDAO.getSeatIDForEachOtherParams(number, row,
                screeningDAO.getScreening(ScreeningID).getAuditoriumID()).getId(), ScreeningID)==null){
            reservation = new Reservation();
            reservation.setScreeningID(ScreeningID);
            reservation.setEmployeeID(2);
            reservation.setReservationType(reservationType);
            reservation.setPaid(payment);
            reservation.setSeatReservationID(seatDAO.getSeatIDForEachOtherParams(number, row, screeningDAO.getScreening(ScreeningID).getAuditoriumID()).getId());
            reservation.setActive(true);

            System.out.println(reservation.toString());
        }else {
            return "redirect:/panel/{id}";
        }
        return "redirect:/panel/finalResult";
    }

    @GetMapping("/finalResult")
    String showReservation(Model model){

        System.out.println(reservation.toString());
        model.addAttribute("resultReservation", reservation);

        return "app/buyTicket/smthNew";
    }

    @PostMapping("/finalResults")
    String makeReservation(){

        System.out.println("yo!!!!");
        reservationDAO.addReservation(reservation);
        return "redirect:/panel";
    }



}
