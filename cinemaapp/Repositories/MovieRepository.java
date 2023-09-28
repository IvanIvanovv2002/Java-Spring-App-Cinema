package com.example.cinemaapp.Repositories;

import com.example.cinemaapp.Models.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findByCategory_Name(String categoryName);
    Optional<Movie> findByMovieName(String movieName);
}
