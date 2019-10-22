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
=======
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_has_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
>>>>>>> 2100dc6986280ec23db05a5f74f6d5f88e087c85
    private List<Project> projects;

}
