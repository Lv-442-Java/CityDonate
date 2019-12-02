package com.softserve.ita.java442.cityDonut.dto.donateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonatesForProjectDto {
    private long id;
    private Timestamp date;
    private double sum;
    private String userFirstName;
    private String userLastName;
}
