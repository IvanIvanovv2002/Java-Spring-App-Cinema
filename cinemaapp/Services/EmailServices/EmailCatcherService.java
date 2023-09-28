package com.example.cinemaapp.Services.EmailServices;

import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Services.UserService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Component
@Aspect
public class EmailCatcherService {

    private final EmailSenderService emailService;
    private final UserService userService;
    private final PasswordEncoder encoder;


    public EmailCatcherService(EmailSenderService emailService, UserService userService, PasswordEncoder encoder) {
        this.emailService = emailService;
        this.userService = userService;
        this.encoder = encoder;
    }

    @Pointcut(value = "execution(* com.example.cinemaapp.Controllers.ReviewsController.reserveTicket(..)) && args(id,mv,email,principal)", argNames = "id,mv,email,principal")
    public void reserveTicketPointcut(Long id, ModelAndView mv, String email, Principal principal) { }

    @Pointcut(value = "execution(* com.example.cinemaapp.Controllers.ProfileController.updateProfile(..)) && " +
   "args(principal,oldPassword,newPassword,confirmPassword,redirectAttributes)",
    argNames = "principal,oldPassword,newPassword,confirmPassword,redirectAttributes")
    public void updateProfilePointcut(Principal principal, String oldPassword,String newPassword, String confirmPassword, RedirectAttributes redirectAttributes) { }

    @After(value = "reserveTicketPointcut(id,mv,email,principal)", argNames = "id,mv,email,principal")
    public void sendEmailAfterReservation(Long id,ModelAndView mv,String email,Principal principal) {
        this.emailService.sendEmailReservation(email);
    }

    @After(value = "updateProfilePointcut(principal,oldPassword,newPassword,confirmPassword,redirectAttributes)",
   argNames = "principal,oldPassword,newPassword,confirmPassword,redirectAttributes")
    public void updateProfile(Principal principal,String oldPassword,String newPassword,String confirmPassword,RedirectAttributes redirectAttributes) {
        User currentUser = this.userService.findUserByUsername(principal.getName()).orElseThrow();
        if (!encoder.matches(newPassword, currentUser.getPassword())) return;
        this.emailService.sendEmailUpdatedAccount(currentUser.getEmail());
    }

}
