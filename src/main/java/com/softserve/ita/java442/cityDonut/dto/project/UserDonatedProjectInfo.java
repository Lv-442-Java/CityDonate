package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDonatedProjectInfo {

    private long id;

    private String name;

    private Media media;

    private ProjectStatus projectStatus;
}
