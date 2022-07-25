package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/post")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService service;

    public PostController(final PostService service) {
        this.service = service;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Post> getAllPosts() {
        return service.getAllPosts();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public Post getSinglePost(@PathVariable("id") Long id) {
        return service.getPost(id);
    }

    @PostMapping
    public Post savePost(@RequestBody Post newPost) {
        return service.addPost(newPost);
    }

    @PreAuthorize("permitAll()")
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

    @PostMapping(path = "/{postId}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadImageToPost(
            @PathVariable("postId") Long postId,
            @RequestParam("file") MultipartFile file) {
        service.uploadImageToPost(postId, file);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{postId}/download")
    public byte[] downloadPostImage(@PathVariable("postId") Long postId) {
        return service.downloadPostImage(postId);
    }
}
