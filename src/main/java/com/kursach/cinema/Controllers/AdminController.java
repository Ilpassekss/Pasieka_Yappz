package com.kursach.cinema.Controllers;


import com.kursach.cinema.DAO.AuditoriumDAO;
import com.kursach.cinema.DAO.MovieDAO;
import com.kursach.cinema.DAO.ScreeningDAO;
import com.kursach.cinema.Model.Auditorium;
import com.kursach.cinema.Model.Movie;
import com.kursach.cinema.Model.Screening;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private MovieDAO movieDAO;
    private ScreeningDAO screeningDAO;

    private AuditoriumDAO auditoriumDAO;

    @Autowired
    public AdminController(MovieDAO movieDAO, ScreeningDAO screeningDAO, AuditoriumDAO auditoriumDAO){
        this.movieDAO = movieDAO;
        this.screeningDAO = screeningDAO;
        this.auditoriumDAO = auditoriumDAO;
    }

    @GetMapping
    String getAdminPanel(){
        return "app/Admin/adminPanel";
    }

    //all films in list
    @GetMapping("/filmsAdmin")
    String getFilms(Model model){
        model.addAttribute("movies", movieDAO.indexMovies());
        return "app/Admin/adminFilms";
    }

    //add new movie in database
    @GetMapping("/newMovie")
    String addNewMovie(Model model){
        model.addAttribute("movie", new Movie());
        return "app/Admin/newFilm";
    }

    @PostMapping("/newMovie")
    public String addMovie(@ModelAttribute("movie") @Valid Movie movie, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "app/Admin/newFilm";
        movieDAO.addMovie(movie);
        return "redirect:/admin/filmsAdmin";
    }



    @GetMapping("filmEdit/{filmID}/editPage")
    public String getFilmEditPage(Model model, @PathVariable("filmID") long filmID){
        model.addAttribute("movie", movieDAO.getMovie(filmID));
        return "app/Admin/editFilmPage";
    }

    @PostMapping("filmEdit/{filmID}")
    public String editFilm(@ModelAttribute("movie") @Valid Movie movie, BindingResult bindingResult, @PathVariable long filmID){
        if(bindingResult.hasErrors())
            return "app/Admin/editFilmPage";

        System.out.println(movie);
        movieDAO.updateMovie(movie);

        return "redirect:/admin/filmsAdmin";
    }

    @PostMapping("/filmEdit/{filmID}/delete")
    String deleteFilm(@PathVariable long filmID){

        movieDAO.deleteMovie(filmID);

        return "redirect:/admin/filmsAdmin";
    }


    //all screenings list
    @GetMapping("/screeningsAdmin")
    String getScreenings(Model model){

        model.addAttribute("screenings", screeningDAO.indexScreenings());
        return "app/Admin/adminScreenings";
    }

    //add new screening into list
    @GetMapping("/newScreening")
    String addNewScreening(Model model){

        //empty objects in which i will add info
        Screening screening = new Screening();
        int filmID = 0;
        int audID = 0;
        String time = "";
        String date = "";
        double standardPrice = 0;
        model.addAttribute("movieID", screening.getMovieID());
        model.addAttribute("audID", screening.getAuditoriumID());
        model.addAttribute("time", screening.getStart_time());
        model.addAttribute("date", screening.getScreening_date());
        model.addAttribute("standardPrice", screening.getStandardPrice());

        //list of movies and cinema halls
        model.addAttribute("movies_list", movieDAO.indexMovies());
        model.addAttribute("auditoriums_list", auditoriumDAO.indexAuditoriums());
        return "app/Admin/newScreening";
    }

   @PostMapping("/newScreening")
   String addScreening(@Valid int movieID, @Valid int audID, @Valid String time,
                       @Valid String date, @Valid double standardPrice){

       Screening screening = new Screening();
       //refactor it using builder pattern
       screening.setMovieID(movieID);
       screening.setAuditoriumID(audID);
       screening.setStart_time(time);
       screening.setScreening_date(date);
       screening.setStandardPrice(standardPrice);
       

       screeningDAO.addScreening(screening);
       return "redirect:/admin/screeningsAdmin";
   }



    @GetMapping("screeningEdit/{screeningID}/scr")
    String getScreeningEditPage(Model model, @PathVariable("screeningID") long screeningID){

        model.addAttribute("screening", screeningDAO.getScreening(screeningID));
        System.out.println(screeningDAO.getScreening(screeningID).getMovieID());
        return "app/Admin/editScreeningPage";
    }

    @PostMapping("screeningEdit/{screeningID}")
    String editScreening(@ModelAttribute("screening") @Valid Screening screening, BindingResult bindingResult, @PathVariable long screeningID){

        if(bindingResult.hasErrors())
            return "app/Admin/editScreeningPage";
        screening.setId(screeningID);

        System.out.println(screening.toString());
        screeningDAO.updateScreening(screening);

        return "redirect:/admin/screeningsAdmin";
    }

    @PostMapping("screeningEdit/{screeningID}/delete")
    String deleteScreening(@PathVariable long screeningID){
        screeningDAO.deleteScreening(screeningID);
        return "redirect:/admin/screeningsAdmin";
    }
}
