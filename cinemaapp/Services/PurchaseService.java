package com.example.cinemaapp.Services;

import com.example.cinemaapp.Models.Entities.Movie;
import com.example.cinemaapp.Models.Entities.Purchase;
import com.example.cinemaapp.Models.Entities.User;
import com.example.cinemaapp.Repositories.PurchaseRepository;
import com.example.cinemaapp.Utils.RandomIdGenerator;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final RandomIdGenerator randomIdGenerator;


    public PurchaseService(PurchaseRepository purchaseRepository, RandomIdGenerator randomIdGenerator) {
        this.purchaseRepository = purchaseRepository;
        this.randomIdGenerator = randomIdGenerator;
    }
    public void createPurchase(User user, Movie movie) {
        String id = this.randomIdGenerator.generate();
        this.purchaseRepository.save(new Purchase(id,user,movie));
    }

    public void deletePurchaseById(String id) {
         this.purchaseRepository.deleteById(id);
    }

}
