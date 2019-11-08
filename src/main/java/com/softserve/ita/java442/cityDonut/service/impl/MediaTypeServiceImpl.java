package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.service.MediaTypeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class MediaTypeServiceImpl implements MediaTypeService {

    public String setMediaType(MediaDto dto) {
        Map<String, Set<String>> typeMap = fillMap();

        String ext = dto.getExtension().toLowerCase();
        typeMap.forEach((key, value) -> {
            if (value.contains(ext)) {
                dto.setMediaType(key);
            }
        });

        return dto.getMediaType();
    }

    private Map<String, Set<String>> fillMap() {
        Map<String, Set<String>> typeMap = new HashMap<>();

        Set<String> imageSet = new HashSet<>();
        imageSet.add("png");
        imageSet.add("tif");
        imageSet.add("jpeg");
        imageSet.add("jpg");
        imageSet.add("gif");
        typeMap.put("image", imageSet);

        Set<String> documentSet = new HashSet<>();
        documentSet.add("doc");
        documentSet.add("docx");
        documentSet.add("pdf");
        documentSet.add("xls");
        documentSet.add("xlsx");
        documentSet.add("ppt");
        documentSet.add("pptx");
        documentSet.add("odt");
        documentSet.add("ods");
        documentSet.add("txt");
        typeMap.put("document", documentSet);

        Set<String> videoSet = new HashSet<>();
        videoSet.add("webm");
        videoSet.add("mpg");
        videoSet.add("mp2");
        videoSet.add("mpe");
        videoSet.add("mpeg");
        videoSet.add("mpv");
        videoSet.add("ogg");
        videoSet.add("mp4");
        videoSet.add("avi");
        videoSet.add("m4p");
        videoSet.add("wmv");
        videoSet.add("mov");
        videoSet.add("qt");
        videoSet.add("flv");
        videoSet.add("swf");
        typeMap.put("video", videoSet);

        return typeMap;
    }
}
