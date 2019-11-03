package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
//@Table(name = "media",
//        indexes = {@Index(name = "file_id", columnList = "file_id", unique = true)})
@Table(name = "media")
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

    @Column(name = "extension", length = 5, nullable = false)
    private String extension;

    @Column(name = "file_id", length = 36, nullable = false)
    private String fileId;

    @Column(name = "create_date")
    private LocalDateTime creationDate;

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
