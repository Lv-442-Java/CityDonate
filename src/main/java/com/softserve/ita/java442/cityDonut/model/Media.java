package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "media",
        indexes = {@Index(name = "media_name_index", columnList = "name", unique = true)})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"name", "mediaType", "project", "storyBoard"})
@ToString(exclude = {"project", "storyBoard"})
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_type_id")
    private MediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_board_id")
    private StoryBoard storyBoard;

}
