package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post/")
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService service;

    public CommentController(final CommentService service) {
        this.service = service;
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable("id") Long id) {
        List<Comment> comments = service.getAllComments(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("{id}/add-comment")
    public ResponseEntity<Comment> saveComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        Comment newComment = service.addComment(id ,comment);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("comment", "/api/v1/post/" + id + "/comment/" + newComment.getId().toString());
        return new ResponseEntity<>(newComment, httpHeaders, HttpStatus.CREATED);
    }
}
