package com.softserve.ita.java442.cityDonut.dto.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse {

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private long size;


}
