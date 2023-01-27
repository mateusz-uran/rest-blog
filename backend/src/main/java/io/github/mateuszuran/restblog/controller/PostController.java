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

@CrossOrigin(origins = "http://localhost:9090/")
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
    @GetMapping("/all")
    public List<Post> getAllPosts() {
        return service.getAllPosts();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/single")
    public Post getSinglePostByParam(@RequestParam Long id) {
        return service.getPost(id);
    }

    @PostMapping
    public Post savePost(@RequestBody Post newPost) {
        return service.addPost(newPost);
    }

    @PutMapping("/update")
    public Post updatePostByParam(@RequestParam Long id, @RequestBody Post post) {
        return service.editPost(id, post);
    }

    /**unused method, implement in future**/
    @PatchMapping("/partial-update")
    public Post partialUpdateByParam(@RequestParam Long id, @RequestBody Post toUpdate) {
        return service.updatePost(id, toUpdate);
    }

    @DeleteMapping("/delete-post")
    public void deletePostByParam(@RequestParam Long id) {
        service.deletePost(id);
    }

    @PostMapping(path = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadImageToPostByParam(@RequestParam Long postId, @RequestParam MultipartFile file) {
        service.uploadImageToPost(postId, file);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{postId}/download")
    public byte[] downloadPostImage(@PathVariable("postId") Long postId) {
        return service.downloadPostImage(postId);
    }
}
