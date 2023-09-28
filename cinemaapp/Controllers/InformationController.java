package com.example.cinemaapp.Controllers;

import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class InformationController {

    private final UserService userService;

    public InformationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getDefaultPage() {
        return "index";
    }

    @GetMapping("/contacts")
    public ModelAndView getContacts(ModelAndView mv, Principal principal) {
        User user = this.userService.findUserByUsername(principal.getName()).orElseThrow();
        mv.addObject("user",user);
        mv.setViewName("contact");
        return mv;
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


}
