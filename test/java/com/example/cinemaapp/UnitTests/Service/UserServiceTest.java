package com.example.cinemaapp.UnitTests.Service;

import com.example.cinemaapp.Models.DTOS.RegistrationFormDTO;
import com.example.cinemaapp.Models.Entities.Role;
import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Models.Enums.RoleType;
import com.example.cinemaapp.Repositories.RoleRepository;
import com.example.cinemaapp.Repositories.UserRepository;
import com.example.cinemaapp.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private  RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private SecurityContextRepository securityRepository;

    @Mock
    private SecurityContextLogoutHandler logoutHandler;

    @Mock
    private HttpServletRequest request;

    private UserService userService;

    private Role userRole;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, roleRepository, encoder, securityRepository);
        userRole = new Role(RoleType.USER);
        user = new User("username", 25, "encodedPassword", "test@example.com",List.of(userRole));
        when(userRepository.findByUsername(eq(null))).thenReturn(Optional.of(user));
    }


    /**
     * By removing  if (this.findUserByEmail(registrationDTO.getEmail()).isPresent()) { return false; } block of code it will always
     * return true, as well as if you include it in your code, it will not pass, meaning this method tests the success and the fail cases.
     */
    @Test
    public void testRegister_Success_Fail() {
        RegistrationFormDTO registrationDTO = new RegistrationFormDTO();
        registrationDTO.setEmail("test@example.com");
        registrationDTO.setPassword("encodedPassword");
        User user = new User("username",25,"encodedPassword","test@example.com", List.of(userRole));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleType(RoleType.USER)).thenReturn(userRole);
        boolean result = userService.register(registrationDTO, mock(HttpServletRequest.class), mock(HttpServletResponse.class));
        assertTrue(result);
        verify(userRepository).save(any(User.class));
        verify(securityRepository).saveContext(any(SecurityContext.class), any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void testDeleteUser_PasswordMatches() {
        when(encoder.matches(eq("encodedPassword"), eq(user.getPassword()))).thenReturn(true);
        boolean result = userService.deleteUser(mock(Principal.class),request, user.getPassword(), mock(RedirectAttributes.class),logoutHandler);
        assertTrue(result);
        verify(userRepository).delete(eq(user));
        verify(logoutHandler).logout(eq(request), isNull(), isNull());
    }


    @Test
    public void testDeleteUser_PasswordNotMatch() {
        when(encoder.matches("encodedPassword", user.getPassword())).thenReturn(false);
        boolean result = userService.deleteUser(mock(Principal.class),request,user.getPassword(),mock(RedirectAttributes.class),logoutHandler);
        assertFalse(result);
    }

    @Test
    public void testUpdateProfile_Success() {
        when(encoder.matches(user.getPassword(),user.getPassword())).thenReturn(true);
        boolean result = userService.updateProfile(mock(Principal.class),user.getPassword(),"newPassword","newPassword",mock(RedirectAttributes.class));
        assertTrue(result);
    }

    @Test
    public void testUpdateProfile_Fail() {
        when(encoder.matches(user.getPassword(),user.getPassword())).thenReturn(false);
        boolean result = userService.updateProfile(mock(Principal.class),user.getPassword(),"newPassword","newPassword",mock(RedirectAttributes.class));
        assertFalse(result);
    }
}
