package com.example.cinemaapp.Models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "video_url")
    private String videoUrl;

    @Column(name = "movie_name",nullable = false)
    private String movieName;

    @Column(nullable = false,columnDefinition = "DECIMAL(5,2)")
    private Double price;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private String premiere;

    @Column(name = "short_description",nullable = false,columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "long_description",nullable = false,columnDefinition = "TEXT")
    private String longDescription;

    @Column(name = "detailed_picture",nullable = false)
    private String detailedPicture;

    public Movie(String movieName, Double price, int length) {
        this.movieName = movieName;
        this.price = price;
        this.length = length;
    }
}
