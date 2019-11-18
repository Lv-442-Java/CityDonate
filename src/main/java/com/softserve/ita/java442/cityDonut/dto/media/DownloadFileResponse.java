package com.softserve.ita.java442.cityDonut.dto.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileResponse {

    private String fileDownloadUri;

    private String fileName;

    private long projectId;

    private String mediaType;
}
