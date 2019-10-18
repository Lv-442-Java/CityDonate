package com.softserve.ita.java442.cityDonut.model;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(name="donate")
public class Donate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date",nullable = false)
    private LocalDateTime date;

    @Column(name = "sum",nullable = false)
    private double sum;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
