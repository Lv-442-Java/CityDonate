package com.softserve.ita.java442.cityDonut.model;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String description;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) &&
                description.equals(comment.description) &&
                date.equals(comment.date);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, date);
    }
}
