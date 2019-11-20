package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"description"})
//@ToString(exclude = {"media"})
@Table(name = "story_board")
public class StoryBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToOne
    @JoinColumn(name = "gallery_id", referencedColumnName = "id")
    private Gallery gallery;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Column(name = "money_spent")
    private long moneySpent;
}
