package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/v1/post/")
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService service;

    public CommentController(final CommentService service) {
        this.service = service;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getAllCommentsInPost(@PathVariable("id") Long id) {
        return service.getAllCommentsByPostId(id);
    }

    @GetMapping("/{id}/comment/{commentId}")
    public Comment getComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId) {
        return service.getCommentByPostId(id, commentId);
    }

    @PostMapping("/{id}/add-comment")
    public Comment addCommentToPost(@PathVariable("id") Long id, @RequestBody Comment newComment) {
        return service.addCommentToPost(id, newComment);
    }

    @PutMapping("/{id}/edit-comment/{commentId}")
    public Comment editComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId, @RequestBody Comment toUpdate) {
        return service.updateComment(id, commentId, toUpdate);
    }

    @DeleteMapping("/{id}/delete-comment/{commentId}")
    public void deleteComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId) {
        service.deleteComment(id, commentId);
    }
}
