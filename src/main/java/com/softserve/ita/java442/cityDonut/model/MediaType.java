package com.softserve.ita.java442.cityDonut.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"mediaType"})
public class MediaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;

//    @OneToMany(mappedBy = "mediaType")
//    private List<Media> mediaList;

    @OneToMany(mappedBy = "mediaType")
    private List<Extension> extensions;
}
