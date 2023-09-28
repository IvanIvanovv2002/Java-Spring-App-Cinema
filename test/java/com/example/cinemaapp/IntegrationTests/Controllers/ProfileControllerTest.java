package com.example.cinemaapp.IntegrationTests.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "demoo",roles = "USER")
public class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testDeleteTicket() throws Exception {
        mockMvc.perform(delete("/profile/delete/{id}","a23567n")
        .with(csrf())).andExpect(redirectedUrl("/profile"));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .put("/profile/update")
        .param("oldPassword", "12345")
        .param("newPassword", "11111")
        .param("confirmPassword", "11111");

        mockMvc.perform(requestBuilder).andExpect(redirectedUrl("/profile"));
    }

    @Test
    public void testDeleteAccount() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.
         delete("/profile/delete")
        .param("password", "12345");

        mockMvc.perform(requestBuilder).andExpect(redirectedUrl("/"));
    }

}
