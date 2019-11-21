package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "gallery")
    private Project project;

    @OneToOne(mappedBy = "gallery")
    private StoryBoard storyBoard;
}
