package com.example.cinemaapp.Models.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationFormDTO {


    @Size(min = 5,max = 25,message = "Username must be between 5 and 25 characters")
    private String username;

    @Min(value = 18,message = "Age must be at least 18")
    private Integer age;

    @Email
    private String email;

    @Size(min = 5,max = 25,message = "Password must be between 5 and 25 characters")
    private String password;

    @Size(min = 10,max = 10)
    private String mobileNumber;


}
