package com.softserve.ita.java442.cityDonut.model;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Builder
@Table(indexes = @Index(columnList = "name, realizationEndDate"))
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_status_id")
    private ProjectStatus projectStatus;

    @OneToMany(mappedBy = "project", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "project", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private List<Donate> donates = new ArrayList<Donate>();

    @OneToMany(mappedBy = "project", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private List<StoryBoard> storyBoards = new ArrayList<StoryBoard>();

    @OneToMany(mappedBy = "project", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private List<Media> media = new ArrayList<Media>();

    @OneToMany(mappedBy = "project", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private List<Document> documents = new ArrayList<Document>();

    @ManyToMany
    @JoinTable(name = "user_has_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<User>();

    @ManyToMany
    @JoinTable(name = "project_has_category",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<Category>();

    public Project() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public LocalDateTime getDonationEndDate() {
        return donationEndDate;
    }

    public void setDonationEndDate(LocalDateTime donationEndDate) {
        this.donationEndDate = donationEndDate;
    }

    public LocalDateTime getRealizationEndDate() {
        return realizationEndDate;
    }

    public void setRealizationEndDate(LocalDateTime realizationEndDate) {
        this.realizationEndDate = realizationEndDate;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Donate> getDonates() {
        return donates;
    }

    public void setDonates(List<Donate> donates) {
        this.donates = donates;
    }

    public List<StoryBoard> getStoryBoards() {
        return storyBoards;
    }

    public void setStoryBoards(List<StoryBoard> storyBoards) {
        this.storyBoards = storyBoards;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(name, project.name) &&
                Objects.equals(locationLatitude, project.locationLatitude) &&
                Objects.equals(locationLongitude, project.locationLongitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, locationLatitude, locationLongitude);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", locationLatitude='" + locationLatitude + '\'' +
                ", locationLongitude='" + locationLongitude + '\'' +
                ", creationDate=" + creationDate +
                ", publicationDate=" + publicationDate +
                ", donationEndDate=" + donationEndDate +
                ", realizationEndDate=" + realizationEndDate +
                '}';
    }
}
