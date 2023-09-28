package com.example.cinemaapp.IntegrationTests.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "vanchovski",roles = "ADMIN")
    public void testAddMovie() throws Exception {
        mockMvc.perform(post("/profile/admin/addMovie")
        .param("movieName","testMovie")
        .param("price","18.00")
        .param("category","Action")
        .param("premiere","18.09.2023")
        .param("videoUrl","1km7lk7vb9a")
        .param("length","145")
        .param("detailedPicture","xxxx")
        .param("shortDescription","xxxx")
        .param("longDescription","xxxx").with(csrf())).andExpect(redirectedUrl("/profile"));
    }

}
