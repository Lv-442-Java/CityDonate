package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"comments", "donates", "storyBoards", "media", "documents", "users", "categories"})
@Table(indexes = @Index(columnList = "name, realizationEndDate"))
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String location;

    @Column(name = "location_latitude", length = 9)
    private String locationLatitude;

    @Column(name = "location_longitude", length = 9)
    private String locationLongitude;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "publication_date")
    private LocalDateTime publicationDate;

    @Column(name = "donation_end_date")
    private LocalDateTime donationEndDate;

    @Column(name = "realization_end_date")
    private LocalDateTime realizationEndDate;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_status_id")
    private ProjectStatus projectStatus;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    @Column(nullable = false)
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

    @OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Media> media;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Document> documents;

    @ManyToMany
    @JoinTable(name = "user_has_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "project_has_category",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;
}
