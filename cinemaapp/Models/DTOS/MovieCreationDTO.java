package com.example.cinemaapp.Models.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreationDTO {

    private String movieName;

    private Double price;

    private String category;

    private String premiere;

    private String videoUrl;

    private Integer length;

    private String detailedPicture;

    private String shortDescription;

    private String longDescription;
}
