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

    @Column(name = "is_name_valid")
    private boolean isNameValid;

    @Column(name = "is_description_valid")
    private boolean isDescriptionValid;

    @Column(name = "is_location_valid")
    private boolean isLocationValid;

    @Column(name = "is_money_needed_valid")
    private boolean isMoneyNeededValid;

    @Column(name = "are_photos_valid")
    private boolean arePhotosValid;

    @Column(name = "are_documents_valid")
    private boolean areDocumentsValid;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
