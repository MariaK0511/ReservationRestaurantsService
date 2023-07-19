package com.reservation_restaurants_service.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends AuditorEntities {

    @Id
    private long id;
    private String review;
    @ManyToOne
    private User user;
    @ManyToOne
    private Restaurant restaurant;
}