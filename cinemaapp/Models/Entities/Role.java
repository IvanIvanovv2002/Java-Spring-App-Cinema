package com.example.cinemaapp.Models.Entities;

import com.example.cinemaapp.Models.Enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(mappedBy = "role",fetch = FetchType.EAGER)
    private List<User> user;

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

}
