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

    @ManyToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Project> projects;

}
