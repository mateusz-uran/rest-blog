package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.service.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/v1/post")
public class CommentController {
    private final CommentService service;

    public CommentController(final CommentService service) {
        this.service = service;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/comments-page-map")
    public Map<String, Object> getAllComments(
            @RequestParam Long id,
            @RequestParam int page,
            @RequestParam int size) {
        return service.getAllComments(id, page, size);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/comment-by-user")
    public Comment getCommentByParamAndUser(@RequestParam Long id, @RequestParam Long commentId, @RequestParam Long userId) {
        return service.getCommentByUser(id, commentId, userId);
    }

    @PostMapping("/add-comment-by-user")
    public Comment addCommentToPostByParamAndUser(@RequestParam Long id, @RequestParam Long userId, @RequestBody Comment newComment) {
        return service.addCommentToPostByUser(id, userId, newComment);
    }

    @PutMapping("/edit-comment-by-user")
    public Comment editCommentByParamAndUser(@RequestParam Long id, @RequestParam Long commentId, @RequestParam Long userId, @RequestBody Comment toUpdate ) {
        return service.updateCommentByUser(id, commentId, userId, toUpdate);
    }

    @DeleteMapping("/delete-comment-by-user")
    public void deleteCommentByUsername(@RequestParam Long id, @RequestParam Long commentId, @RequestParam Long userId) {
        service.deleteCommentByUser(id, commentId, userId);
    }
}
