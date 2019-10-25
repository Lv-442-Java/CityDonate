package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl service;


   @GetMapping("/{id}/all")
    public ResponseEntity<List<CommentDto>> showAllComments(@PathVariable("id") long id){


        //return service.showComments(id);
       return ResponseEntity.status(HttpStatus.OK).body(service.showComments(id));
        //return  new ResponseEntity<>(service.showComments(id),HttpStatus.OK);
   }

   @PostMapping("/send")
    public ResponseEntity<CommentDto> sendComment(@RequestBody CommentDto comment){

       return ResponseEntity.status(HttpStatus.CREATED).body(service.sendComment(comment));
   }
}
