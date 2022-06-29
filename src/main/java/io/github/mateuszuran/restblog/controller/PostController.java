package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.service.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService service;

    public PostController(final PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = service.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getPost(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody Post post) {
        Post newPost = service.addPost(post);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("post", "/api/v1/post/" + newPost.getId().toString());
        return new ResponseEntity<>(newPost, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        service.editPost(id, post);
        return new ResponseEntity<>(service.getPost(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") Long id) {
        service.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
