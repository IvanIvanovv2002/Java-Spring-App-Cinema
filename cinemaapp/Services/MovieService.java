package com.example.cinemaapp.Services;


import com.example.cinemaapp.Models.Entities.Movie;
import com.example.cinemaapp.Repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
       return this.movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return this.movieRepository.findById(id);
    }

    public List<Movie> getMoviesByCategoryName(String categoryName) {
        return this.movieRepository.findByCategory_Name(categoryName);
    }
    public Optional<Movie> findMovieByName(String name) {
       return Optional.of(this.movieRepository.findByMovieName(name).orElseThrow());
    }

    public void addMovie(Movie movie) {
        this.movieRepository.save(movie);
    }
}
