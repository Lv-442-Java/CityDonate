package com.softserve.ita.java442.cityDonut.dto.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {

    private long id;

    private String name;
}