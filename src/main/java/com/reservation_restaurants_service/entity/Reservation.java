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
import java.lang.Object;

import lombok.*;

@Entity
@Table(name = "reservations")
@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends AuditorEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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