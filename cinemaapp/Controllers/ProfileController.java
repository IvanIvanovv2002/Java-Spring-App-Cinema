package com.example.cinemaapp.Controllers;

import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Services.PurchaseService;
import com.example.cinemaapp.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {
    private final UserService userService;
    private final PurchaseService purchaseService;

    public ProfileController(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService = purchaseService;

    }

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal, ModelAndView mv) {
        User user = this.userService.findUserByUsername(principal.getName()).orElseThrow();
        mv.setViewName("profile");
        return mv.addObject("user",user);

    }

    @DeleteMapping("/profile/delete/{id}")
    @Transactional
    public ModelAndView removeTicket(@PathVariable String id) {
        this.purchaseService.deletePurchaseById(id);
        return new ModelAndView("redirect:/profile");
    }

    @PutMapping("/profile/update")
    @Transactional
    public ModelAndView updateProfile(Principal principal, String oldPassword, String newPassword, String confirmPassword, RedirectAttributes redirect) {
        this.userService.updateProfile(principal,oldPassword,newPassword,confirmPassword,redirect);
        return new ModelAndView("redirect:/profile");
    }

    @DeleteMapping("/profile/delete")
    @Transactional
    public ModelAndView deleteProfile(String password,Principal principal, HttpServletRequest request,RedirectAttributes redirect,SecurityContextLogoutHandler logoutHandler) {
        boolean accountRemoved = this.userService.deleteUser(principal, request, password, redirect,logoutHandler);
        if (!accountRemoved) { return new ModelAndView("redirect:/profile"); }
        return new ModelAndView("redirect:/");
    }

}
