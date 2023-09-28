package com.example.cinemaapp.Controllers;

import com.example.cinemaapp.Exceptions.MediaNotFoundException;
import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Services.EmailServices.EmailSenderService;
import com.example.cinemaapp.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MediaController {
    private final UserService userService;
    private final EmailSenderService emailSenderService;

    public MediaController(UserService userService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/MediaNotFound")
    public String mediaPage() {
        throw new MediaNotFoundException("");
    }

    @PostMapping("/contact/sendMessage")
    public ModelAndView sendMessage(String email,String sendMessage) {
        User user = this.userService.findUserByEmail(email).orElseThrow();
        this.emailSenderService.sendMessageToSupport(user.getEmail(),sendMessage);
        return new ModelAndView("redirect:/contacts");
    }

}
