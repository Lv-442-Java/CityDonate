package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"moderatorProjects", "userProjects", "comments", "donates"})
@EqualsAndHashCode(exclude = {"firstName", "lastName", "password", "moderatorProjects", "userProjects", "users", "comments", "donates"})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String password;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Project> moderatorProjects;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Project> userProjects;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Donate> donates;
}
