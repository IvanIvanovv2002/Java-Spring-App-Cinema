package com.example.cinemaapp.Controllers;

import com.example.cinemaapp.Models.DTOS.RegistrationFormDTO;

import com.example.cinemaapp.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthenticationController {

    private final UserService userService;


    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }


    @GetMapping("/login")
    public ModelAndView loginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @PostMapping("/login_failed")
    public ModelAndView loginError(ModelAndView modelAndView,RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("redirect:/login");
        redirectAttributes.addFlashAttribute("error",true);
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid  RegistrationFormDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirect ,HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mv.setViewName("redirect:/register");
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO",bindingResult);
            redirect.addFlashAttribute("registerDTO",registerDTO);
            return mv;
        } else if (!userService.register(registerDTO, request, response)) {
            redirect.addFlashAttribute("userExists",true);
            mv.setViewName("redirect:/register");
            return mv;
        };
        mv.setViewName("redirect:/login");
        return mv;
    }

    @ModelAttribute(name = "registerDTO")
    public RegistrationFormDTO registrationFormDTO() {
        return new RegistrationFormDTO();
    }
}
