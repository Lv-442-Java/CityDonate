package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.service.MediaTypeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MediaTypeServiceImpl implements MediaTypeService {

    public String setMediaType(MediaDto dto) {
        Map typeMap = fillMap();
        String ext = dto.getExtension().toLowerCase();
        String val = (String) typeMap.get(ext);
        dto.setMediaType(val);
        return dto.getMediaType();
    }

    private Map fillMap() {
        Map<String, String> typeMap = new HashMap<>();

        typeMap.put("png", "image");
        typeMap.put("tif", "image");
        typeMap.put("jpeg", "image");
        typeMap.put("jpg", "image");
        typeMap.put("gif", "image");
        typeMap.put("doc", "document");
        typeMap.put("docx", "document");
        typeMap.put("pdf", "document");
        typeMap.put("xls", "document");
        typeMap.put("xlsx", "document");
        typeMap.put("ppt", "document");
        typeMap.put("pptx", "document");
        typeMap.put("odt", "document");
        typeMap.put("ods", "document");
        typeMap.put("txt", "document");
        typeMap.put("webm", "video");
        typeMap.put("mpg", "video");
        typeMap.put("mp2", "video");
        typeMap.put("mpe", "video");
        typeMap.put("mpeg", "video");
        typeMap.put("mpv", "video");
        typeMap.put("ogg", "video");
        typeMap.put("mp4", "video");
        typeMap.put("avi", "video");
        typeMap.put("m4p", "video");
        typeMap.put("wmv", "video");
        typeMap.put("mov", "video");
        typeMap.put("qt", "video");
        typeMap.put("flv", "video");
        typeMap.put("swf", "video");
        return typeMap;
    }
}
