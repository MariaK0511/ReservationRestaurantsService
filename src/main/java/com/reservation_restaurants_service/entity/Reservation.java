package com.reservation_restaurants_service.entity;

import com.reservation_restaurants_service.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Restaurant restaurant;
    private LocalDateTime creationTime;
    private long guests;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "time_status_change")
    private LocalDateTime timeOfStatusChange;
    @ManyToOne
    private User user;
}