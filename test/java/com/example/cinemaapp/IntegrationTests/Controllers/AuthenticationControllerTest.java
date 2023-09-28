package com.example.cinemaapp.IntegrationTests.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testRegistration_Success() throws Exception {
        mockMvc.perform(post("/register")
        .param("username","tested2")
        .param("age","22")
        .param("email","tested@abv.ru")
        .param("password","12345")
        .param("mobileNumber","0988387123").with(csrf())).andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testRegistration_Fail_UserExists() throws Exception {
        mockMvc.perform(post("/register")
        .param("username","misho203")
        .param("age","32")
        .param("email","misho@abv.bg")
        .param("password","12345")
        .param("mobileNumber","0988387123").with(csrf())).andExpect(redirectedUrl("/register"));
    }

    @Test
    public void testLogin_Success() throws Exception {
        mockMvc.perform(post("/login")
        .param("email","misho@abv.bg")
        .param("password","12345")
       .with(csrf())).andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLogin_InvalidParameters() throws Exception {
        mockMvc.perform(post("/login_failed")
        .param("email","failed@abv.bg")
        .param("password","12345")
        .with(csrf())).andExpect(redirectedUrl("/login"));
    }
}
