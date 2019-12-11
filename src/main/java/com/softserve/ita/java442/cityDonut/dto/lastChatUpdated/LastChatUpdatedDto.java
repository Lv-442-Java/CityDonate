package com.softserve.ita.java442.cityDonut.dto.lastChatUpdated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LastChatUpdatedDto {

    private long id;

    private Timestamp dateTime;

    private long projectId;

    private long userId;

}
