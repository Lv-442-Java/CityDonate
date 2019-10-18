package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConctructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"moderatorProjects", "userProjects", "comments", "donates"})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Donate> donates;
}
