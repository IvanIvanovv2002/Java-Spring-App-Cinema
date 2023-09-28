package com.example.cinemaapp.Services;

import com.example.cinemaapp.Models.Entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(@RequestParam("email") String email) throws UsernameNotFoundException {
      Optional<User> user = userService.findUserByEmail(email);

      return map(user.get());
    }

    private UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),extractedAuthorities(user));
    }

    private List<SimpleGrantedAuthority> extractedAuthorities(User user) {
        return user.getRole().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleType().name())).toList();
    }

}
