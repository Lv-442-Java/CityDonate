package com.softserve.ita.java442.cityDonut.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonatedUserProject {

    private long id;

    private long donateCount;

    private double sum;

    private long projectId;

    private long userId;

}
