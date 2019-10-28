package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"name", "description", "location", "money_needed", "photos", "documents"})
@ToString(exclude = {"project"})
@Table(name = "fields_check")
public class FieldsCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private boolean name;

    @Column(name = "description")
    private boolean description;

    @Column(name = "location")
    private boolean location;

    @Column(name = "money_needed")
    private boolean moneyNeeded;

    @Column(name = "photos")
    private boolean photos;

    @Column(name = "documents")
    private boolean documents;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
