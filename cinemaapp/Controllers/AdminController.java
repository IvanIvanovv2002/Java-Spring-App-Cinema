package com.example.cinemaapp.Controllers;

import com.example.cinemaapp.Models.DTOS.MovieCreationDTO;
import com.example.cinemaapp.Models.Entities.Movie;
import com.example.cinemaapp.Services.CategoryService;
import com.example.cinemaapp.Services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    private final CategoryService categoryService;
    private final MovieService movieService;
    private final ModelMapper mapper;

    public AdminController(CategoryService categoryService, MovieService movieService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.movieService = movieService;
        this.mapper = mapper;
    }

    @PostMapping("/profile/admin/addMovie")
    public ModelAndView addMovie(MovieCreationDTO movieCreationDTO, RedirectAttributes redirect) {
        Movie mappedMovie = mapper.map(movieCreationDTO, Movie.class);
        mappedMovie.setCategory(this.categoryService.findCategoryByName(movieCreationDTO.getCategory()).orElseThrow());
        this.movieService.addMovie(mappedMovie);
        redirect.addFlashAttribute("successfulAddedMovie",true);
        return new ModelAndView("redirect:/profile");
    }

    @ModelAttribute("movieDTO")
    public MovieCreationDTO movieCreationDTO() {
        return new MovieCreationDTO();
    }
}
