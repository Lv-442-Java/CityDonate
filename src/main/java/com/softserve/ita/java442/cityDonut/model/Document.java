package com.softserve.ita.java442.cityDonut.model;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Entity
@Builder
@Data
@Table(name="document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private double sum;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id",nullable = false)
    private Project project;
}
