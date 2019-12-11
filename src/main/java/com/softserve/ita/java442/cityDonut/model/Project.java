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
@EqualsAndHashCode(exclude = {"description", "location", "locationLatitude", "locationLongitude", "creationDate", "publicationDate",
        "donationEndDate", "realizationEndDate", "projectStatus", "comments", "donates", "storyBoards", "gallery", "documents", "moderators", "categories", "subscribedUsers", "lastChatUpdatedTimes"})
@NoArgsConstructor
@ToString(exclude = {"comments", "donates", "storyBoards", "gallery", "documents", "moderators", "categories", "subscribedUsers", "lastChatUpdatedTimes"})
@Table(indexes = @Index(columnList = "name, realization_end_date"))
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String location;

    @Column(name = "location_latitude", precision = 9, scale = 6)
    private double locationLatitude;

    @Column(name = "location_longitude", precision = 9, scale = 6)
    private double locationLongitude;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "publication_date")
    private Timestamp publicationDate;

    @Column(name = "donation_end_date")
    private Timestamp donationEndDate;

    @Column(name = "realization_end_date")
    private Timestamp realizationEndDate;

    @Column(name = "money_needed")
    private long moneyNeeded;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
             CascadeType.REFRESH})
    @JoinColumn(name = "project_status_id")
    private ProjectStatus projectStatus;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
             CascadeType.REFRESH})
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Donate> donates;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<StoryBoard> storyBoards;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<LastChatUpdated> lastChatUpdatedTimes;

    @OneToOne
    @JoinColumn(name = "gallery_id", referencedColumnName = "id")
    private Gallery gallery;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "moderator_has_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "moderator_id"))
    private List<User> moderators;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_has_category",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_subscribed_to_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> subscribedUsers;
}
