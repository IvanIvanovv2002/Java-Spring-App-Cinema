package com.example.cinemaapp.Repositories;

import com.example.cinemaapp.Models.Entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,String> {
    void deleteById(String id);
}
