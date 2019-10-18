package com.softserve.ita.java442.cityDonut.model;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "media",
        indexes = {@Index(name = "media_name_index", columnList = "name", unique = true)})
@Builder
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private MediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoryBoard storyBoard;

}
