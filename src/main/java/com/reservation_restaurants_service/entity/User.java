package com.reservation_restaurants_service.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String nickname;
    private String email;
    private String password;
    private long phoneNumber;
    @OneToMany
    List<Reservation> reservations;
}