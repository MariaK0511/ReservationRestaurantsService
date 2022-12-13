package com.reservation_restaurants_service.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String surname;
    @Column(unique = true)
    private String nickname;
    @Column(unique = true)
    private String email;
    private String password;
    private long phoneNumber;
    @OneToMany
    List<Reservation> reservations;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Role> roleList;

    @Enumerated(EnumType.STRING)
    private Status status;
}