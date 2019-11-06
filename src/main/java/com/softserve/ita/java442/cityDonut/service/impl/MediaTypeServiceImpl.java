package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.repository.MediaTypeRepository;
import com.softserve.ita.java442.cityDonut.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MediaTypeServiceImpl implements MediaService {

    @Autowired
    MediaTypeRepository mediaTypeRepository;

    private <K, V> V getValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getKey().equals(value)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public String setMediaType(MediaDto dto) {
        Map typeMap = fillMap();

        String ext = dto.getExtension().toLowerCase();
        String mediaType = getValue(typeMap, ext);
//        String mediaType = null;
//        for (Map.Entry<String, Set> entry : typeMap.entrySet()) {
//            Set value = entry.getValue();
//            if(value == mediaType){
//                dto.setMediaType(value);
//            }
//        }
//        Set keys = typeMap.keySet();
//        for (Iterator i = keys.iterator(); i.hasNext(); ) {
//            String key = (String) i.next();
//            Set value = (Set) typeMap.get(key);
//            if (value == ext) {
//                dto.setMediaType(value);
//            }
//        }
        String val = (String)typeMap.get(ext);
        dto.setMediaType(mediaType);
        return dto.getMediaType();
    }

//    public static void main(String[] args) {
//        MediaDto dto = new MediaDto();
//        dto.setExtension("jpg");
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            String k = entry.getKey();
//            String v = entry.getValue();
//            System.out.println("Key: " + k + ", Value: " + v);
//        }
//    }

    private Map fillMap() {
        Map<String, String> typeMap = new HashMap<>();

        typeMap.put("png", "image");
        typeMap.put("tif", "image");
        typeMap.put("jpeg", "image");
        typeMap.put("jpg", "image");
        typeMap.put("gif", "image");

//        Set<String> imageSet = new HashSet<>();
//        imageSet.add("png");
//        imageSet.add("tif");
//        imageSet.add("jpeg");
//        imageSet.add("jpg");
//        imageSet.add("gif");
        //    typeMap.put("image", imageSet);

//        Set<String> documentSet = new HashSet<>();
//        documentSet.add("doc");
//        documentSet.add("docx");
//        documentSet.add("pdf");
//        documentSet.add("xls");
//        documentSet.add("xlsx");
//        documentSet.add("ppt");
//        documentSet.add("pptx");
//        documentSet.add("odt");
//        documentSet.add("ods");
//        documentSet.add("txt");
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
        //     typeMap.put("document", documentSet);

//        Set<String> videoSet = new HashSet<>();
//        videoSet.add(".webm");
//        videoSet.add(".mpg");
//        videoSet.add(".mp2");
//        videoSet.add(".mpe");
//        videoSet.add(".mpeg");
//        videoSet.add(".mpv");
//        videoSet.add(".ogg");
//        videoSet.add(".mp4");
//        videoSet.add(".avi");
//        videoSet.add(".m4p");
//        videoSet.add(".wmv");
//        videoSet.add(".mov");
//        videoSet.add(".qt");
//        videoSet.add(".flv");
//        videoSet.add(".swf");
//        typeMap.put("video", videoSet);

        typeMap.put(".webm", "video");
        typeMap.put(".mpg", "video");
        typeMap.put(".mp2", "video");
        typeMap.put(".mpe", "video");
        typeMap.put(".mpeg", "video");
        typeMap.put(".mpv", "video");
        typeMap.put(".ogg", "video");
        typeMap.put(".mp4", "video");
        typeMap.put(".avi", "video");
        typeMap.put(".m4p", "video");
        typeMap.put(".wmv", "video");
        typeMap.put(".mov", "video");
        typeMap.put(".qt", "video");
        typeMap.put(".flv", "video");
        typeMap.put(".swf", "video");

        return typeMap;
    }
}
