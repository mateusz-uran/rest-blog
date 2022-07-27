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
@RequestMapping("/api/v1/post")
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService service;

    public CommentController(final CommentService service) {
        this.service = service;
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/comments")
    public List<Comment> getAllCommentsInPostByParam(@RequestParam Long id) {
        return service.getAllCommentsByPostId(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/comment")
    public Comment getCommentByParam(@RequestParam Long id, @RequestParam Long commentId) {
        return service.getCommentByPostId(id, commentId);
    }

    @PostMapping("/add-comment")
    public Comment addCommentToPostByParam(@RequestParam Long id, @RequestBody Comment newComment) {
        return service.addCommentToPost(id, newComment);
    }

    @PutMapping("/edit-comment")
    public Comment editCommentByParam(@RequestParam Long id, @RequestParam Long commentId, @RequestBody Comment toUpdate) {
        return service.updateComment(id, commentId, toUpdate);
    }

    @DeleteMapping("/delete-comment")
    public void deleteCommentByParam(@RequestParam Long id, @RequestParam Long commentId) {
        service.deleteComment(id, commentId);
    }
}
