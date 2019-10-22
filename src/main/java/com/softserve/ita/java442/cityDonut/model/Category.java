package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"projects"})
@EqualsAndHashCode(exclude = {"projects"})

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category", length = 35, nullable = false)
    private String category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_has_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

}
