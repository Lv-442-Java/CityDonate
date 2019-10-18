package com.softserve.ita.java442.cityDonut.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(name="story_board")
public class StoryBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="description", length=1000)
    private String description;

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    @NotNull
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private long projectId;
}
