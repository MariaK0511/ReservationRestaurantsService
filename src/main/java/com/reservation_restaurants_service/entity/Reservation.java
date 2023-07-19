package com.reservation_restaurants_service.entity;

import com.reservation_restaurants_service.enums.Status;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation extends AuditorEntities {

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