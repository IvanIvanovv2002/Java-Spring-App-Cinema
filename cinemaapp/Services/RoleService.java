package com.example.cinemaapp.Services;

import com.example.cinemaapp.Models.Enums.RoleType;
import com.example.cinemaapp.Models.Entities.Role;
import com.example.cinemaapp.Repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        if (roleRepository.count() > 0) return;
        this.roleRepository.save(new Role(RoleType.USER));
        this.roleRepository.save(new Role(RoleType.ADMIN));
    }
}

