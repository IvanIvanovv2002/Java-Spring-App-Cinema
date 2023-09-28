package com.example.cinemaapp.Controllers.ErrorHandler;

import com.example.cinemaapp.Exceptions.MediaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MediaNotFoundException.class)
    public ModelAndView modelAndView() {
        return new ModelAndView("error/MediaNotFound");
    }

}
