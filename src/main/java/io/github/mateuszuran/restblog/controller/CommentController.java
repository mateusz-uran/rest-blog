package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
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

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable("postId") Long postId) {
        List<Comment> comments = service.getAllComments(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        Comment comment = service.getCommentById(postId, commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping("{postId}/add-comment")
    public ResponseEntity<Comment> saveComment(@PathVariable("postId") Long postIdd, @RequestBody Comment comment) {
        Comment newComment = service.addComment(postIdd ,comment);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("comment", "/api/v1/post/" + postIdd + "/comment/" + newComment.getId().toString());
        return new ResponseEntity<>(newComment, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<Comment> updatePost(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestBody Comment comment) {
        service.editComment(postId, commentId, comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("commentId") Long commentId) {
        service.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
