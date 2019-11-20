package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.service.impl.CommentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/project/{id}/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl service;

    @GetMapping()
    public ResponseEntity<List<CommentDto>> showAllComments(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.showComments(id));
    }

    @PostMapping()
    public ResponseEntity<CommentDto> sendComment(@RequestBody CommentDto comment, @PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.sendComment(comment, id));
   }

   @DeleteMapping("/{commentId}")
   public ResponseEntity<CommentDto> deleteComment(@PathVariable long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteComment(commentId));
   }

   @PutMapping("/{commentId}")
   public ResponseEntity<CommentDto> editComment(@RequestBody CommentDto commentText, @PathVariable long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.editComment(commentId, commentText));
   }

}
