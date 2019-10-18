package com.softserve.ita.java442.cityDonut.model;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@Table(name="story_board")
public class StoryBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="description", length=1000)
    private String description;

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    @NotNull
    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryBoard that = (StoryBoard) o;
        return id.equals(that.id) &&
                Objects.equals(description, that.description) &&
                date.equals(that.date) &&
                projectId.equals(that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, projectId);
    }

    @Override
    public String toString() {
        return "StoryBoard{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", projectId=" + projectId +
                '}';
    }
}
