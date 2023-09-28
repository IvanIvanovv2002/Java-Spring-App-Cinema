package com.example.cinemaapp.Services;

import com.example.cinemaapp.Models.DTOS.RegistrationFormDTO;
import com.example.cinemaapp.Models.Enums.RoleType;
import com.example.cinemaapp.Models.Entities.Role;
import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Repositories.RoleRepository;
import com.example.cinemaapp.Repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;
    private final SecurityContextRepository securityRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, SecurityContextRepository securityRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userDetailsService = new UserDetailServiceImpl(this);
        this.securityRepository = securityRepository;
    }


    public Optional<User> findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
    public Optional<User> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public boolean register(RegistrationFormDTO registrationDTO, HttpServletRequest request, HttpServletResponse response) {
        if (this.findUserByEmail(registrationDTO.getEmail()).isPresent()) { return false; }

        Role role = this.roleRepository.findByRoleType(RoleType.USER);
        User user = new User(registrationDTO.getUsername(), encoder.encode(registrationDTO.getPassword()),
        registrationDTO.getEmail(),registrationDTO.getAge(), registrationDTO.getMobileNumber());
        user.setRole(List.of(role));

        this.userRepository.save(user);
        UserDetails currentUser = this.userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser,currentUser.getPassword(),currentUser.getAuthorities());

        SecurityContextHolderStrategy securityHolder = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext securityContext = securityHolder.getContext();
        securityContext.setAuthentication(authentication);

        this.securityRepository.saveContext(securityContext,request,response);
        return true;
    }

    public boolean deleteUser(Principal principal,HttpServletRequest request,String password,RedirectAttributes redirect,SecurityContextLogoutHandler logoutHandler) {
        User user = currentUser(principal);
        if (!encoder.matches(password,user.getPassword())) { redirect.addFlashAttribute("wrongPasswordMatch",true); return false; }
        this.userRepository.delete(user);;
        logoutHandler.logout(request, null, null);
        return true;
    }

    public boolean updateProfile(Principal principal, String oldPassword, String newPassword, String confirmPassword, RedirectAttributes redirect) {
        if (!this.encoder.matches(oldPassword,currentUser(principal).getPassword())) {redirect.addFlashAttribute("wrongPassword",true); return false; }
        if (!newPassword.equals(confirmPassword)) { redirect.addFlashAttribute("wrongPasswordConfirmation",true); return false; }
        currentUser(principal).setPassword(this.encoder.encode(newPassword));
        return true;
    }

    private User currentUser(Principal principal) {
        return this.findUserByUsername(principal.getName()).orElseThrow();
    }


    @PostConstruct
    public void initSomeUsers() {
        if (userRepository.count() > 0) return;
        Role adminRole = roleRepository.findByRoleType(RoleType.ADMIN);
        Role userRole = roleRepository.findByRoleType(RoleType.USER);
        User user1 = new User("vanchovski",encoder.encode("12345"),"ivan_ivanovv2002@abv.bg", List.of(adminRole),22,"0988387487");
        User user2 = new User("misho203",encoder.encode("12345"),"misho@abv.bg", List.of(userRole),32,"0899345346");
        userRepository.save(user1);
        userRepository.save(user2);
    }
}
