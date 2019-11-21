package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.fieldsCheck.FieldsCheckDto;
import com.softserve.ita.java442.cityDonut.service.FieldsCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fieldsCheck")
public class FieldsCheckController {
    @Autowired
    FieldsCheckService fieldsCheckService;

    @GetMapping("/get/{id}")
    public ResponseEntity<List<FieldsCheckDto>> getAllCategories(@PathVariable long id) {
        return new ResponseEntity<>(fieldsCheckService.getAllByProjectId(id), HttpStatus.OK);
    }
}
