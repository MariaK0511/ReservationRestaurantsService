package com.reservation_restaurants_service.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    private long id;
    private String review;
    @ManyToOne
    private User user;
    @ManyToOne
    private Restaurant restaurant;
}
