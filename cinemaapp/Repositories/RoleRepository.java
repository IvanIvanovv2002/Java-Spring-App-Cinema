package com.example.cinemaapp.Repositories;

import com.example.cinemaapp.Models.Enums.RoleType;
import com.example.cinemaapp.Models.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleType(RoleType roleType);
}
