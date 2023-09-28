package com.example.cinemaapp.UnitTests.Service;

import com.example.cinemaapp.Models.Entities.Movie;
import com.example.cinemaapp.Models.Entities.Purchase;
import com.example.cinemaapp.Models.Entities.Role;
import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Models.Enums.RoleType;
import com.example.cinemaapp.Repositories.PurchaseRepository;
import com.example.cinemaapp.Services.PurchaseService;
import com.example.cinemaapp.Utils.RandomIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private RandomIdGenerator randomIdGenerator;

    private PurchaseService purchaseService;

    private User user;

    private Movie movie;


    @BeforeEach
    public void setup() {
        this.purchaseService = new PurchaseService(purchaseRepository,randomIdGenerator);
        when(randomIdGenerator.generate()).thenReturn("I765DKA");
        user = new User("username", 25, "encodedPassword", "test@example.com", List.of(new Role(RoleType.USER)));
        movie = new Movie("title",17.00,125);
    }

    @Test
    public void testCreatePurchase() {
        this.purchaseService.createPurchase(user,movie);
        verify(randomIdGenerator, times(1)).generate();
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

}
