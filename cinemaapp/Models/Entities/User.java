package com.example.cinemaapp.Models.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false,name = "mobile_number")
    private String mobileNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Purchase> purchases;

    public User(String username, String password, String email, List<Role> role,Integer age,String mobileNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.created = LocalDate.now();
    }

    public User(String username, String password, String email,Integer age,String mobileNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.created = LocalDate.now();
    }

    public User(String username, Integer age, String password, String email,List<Role> roles) {
        this.username = username;
        this.age = age;
        this.password = password;
        this.email = email;
        this.role = roles;
    }
}
