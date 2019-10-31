package com.softserve.ita.java442.cityDonut.dto.donateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonateDto {

    private long id;

    private LocalDateTime date;

    private double sum;

    private long userId;

    private long projectId;
}
