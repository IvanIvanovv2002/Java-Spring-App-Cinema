package com.example.cinemaapp.Controllers;

import com.example.cinemaapp.Models.Entities.Movie;
import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Services.MovieService;
import com.example.cinemaapp.Services.PurchaseService;
import com.example.cinemaapp.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class ReviewsController {

    private final UserService userService;
    private final MovieService movieService;
    private final PurchaseService purchaseService;

    public ReviewsController(UserService userService, MovieService movieService, PurchaseService purchaseService) {
        this.userService = userService;
        this.movieService = movieService;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/reviews")
    public ModelAndView reviews(ModelAndView modelAndView, @RequestParam(name = "categoryName",defaultValue = "All") String movieCategoryType) {
        modelAndView.addObject("filteredMovies",filteredMovies(movieCategoryType));
        modelAndView.setViewName("review");
        return modelAndView;
    }

    @PostMapping("/filterMovies")
    public ModelAndView filteredMovies(@RequestParam String categoryName, ModelAndView modelAndView) {
        modelAndView.addObject("filteredMovies",this.filteredMovies(categoryName));
        modelAndView.setViewName("review");
        return modelAndView;
    }

    @GetMapping("/single/{id}")
    public ModelAndView movieDetails(@PathVariable Long id, ModelAndView modelAndView,Principal principal) {
        modelAndView.addObject("movie",this.movieService.getMovieById(id).orElseThrow());
        modelAndView.addObject("user",this.userService.findUserByUsername(principal.getName()).orElseThrow());
        modelAndView.setViewName("single");
        return modelAndView;
    }

    @PostMapping("/single/{id}/reservation")
    public ModelAndView reserveTicket(@PathVariable Long id, ModelAndView mv, @RequestParam("email") String email, Principal principal) {
        User user = this.userService.findUserByUsername(principal.getName()).orElseThrow();
        Movie movie = this.movieService.getMovieById(id).orElseThrow();
        this.purchaseService.createPurchase(user,movie);
        mv.setViewName("single");
        return mv.addObject("movie",movie);
    }

    @ModelAttribute(name = "filteredMovies")
    public List<Movie> filteredMovies(@RequestParam(name = "categoryName",defaultValue = "All") String categoryName) {
        List<Movie> allMovies = this.movieService.getAllMovies();
        return categoryName.equals("All") ? allMovies : this.movieService.getMoviesByCategoryName(categoryName);
    }

    @ModelAttribute(name = "slidedMovies")
    public List<Movie> slidedMovies() {
        Movie venom = this.movieService.findMovieByName("Venom").orElseThrow();
        Movie justiceLeague = this.movieService.findMovieByName("Justice League").orElseThrow();
        Movie avengers = this.movieService.findMovieByName("Avengers").orElseThrow();
        return List.of(venom, justiceLeague,avengers);
    }

}
