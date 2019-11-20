package com.softserve.ita.java442.cityDonut.model;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "media")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"name", "mediaType", "project", "storyBoard"})
@ToString(exclude = {"project", "storyBoard", "mediaType"})
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_id", length = 36, nullable = false)
    private String fileId;

    @Column(name = "create_date")
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "media_type_id")
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "extension_id")
    private Extension extension;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_board_id")
    private StoryBoard storyBoard;
}
