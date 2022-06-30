package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService service;

    public PostController(final PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return service.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getSinglePost(@PathVariable("id") Long id) {
        return service.getPost(id);
    }

    @PostMapping
    public Post savePost(@RequestBody Post newPost) {
        return service.addPost(newPost);
    }

    @PutMapping("/{id}")
    public Post replacePost(@PathVariable("id") Long id, @RequestBody Post post) {
        return service.editPost(id, post);
    }

    @PatchMapping("/{id}")
    public Post partialUpdate(@PathVariable("id") Long id, @RequestBody Post post) {
        return service.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        service.deletePost(id);
    }
}
