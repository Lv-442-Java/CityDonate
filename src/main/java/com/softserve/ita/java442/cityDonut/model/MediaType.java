package com.softserve.ita.java442.cityDonut.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "media_type")
public class MediaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "media_type", length = 5, nullable = false)
    private String media_type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mediaType")
    private List<MediaType> mediaTypeList;
}