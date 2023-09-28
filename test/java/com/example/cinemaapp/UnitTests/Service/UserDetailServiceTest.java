package com.example.cinemaapp.UnitTests.Service;

import com.example.cinemaapp.Models.Entities.Role;
import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Models.Enums.RoleType;
import com.example.cinemaapp.Repositories.RoleRepository;
import com.example.cinemaapp.Repositories.UserRepository;
import com.example.cinemaapp.Services.UserDetailServiceImpl;
import com.example.cinemaapp.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {

    private UserService userService;
    private UserDetailServiceImpl userDetailService;

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private SecurityContextRepository securityRepository;

    private User user;

    @BeforeEach
    public void setup() {
        this.userService = new UserService(userRepository,roleRepository,encoder,securityRepository);
        this.userDetailService = new UserDetailServiceImpl(userService);
        user = new User("username", 25, "encodedPassword", "test@example.com", List.of(new Role(RoleType.USER)));
    }

    @Test
    public void testLoadByUsernameWithValidUser() {
        when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void testLoadByUsernameThrows() {
        when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userDetailService.loadUserByUsername(user.getEmail()));
    }

}
