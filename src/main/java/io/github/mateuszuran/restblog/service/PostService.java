package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(final PostRepository repository) {
        this.repository = repository;
    }

    public Post addPost(Post post) {
        return repository.save(post);
    }

    public List<Post> getAllPosts() {
        return  repository.findAll();
    }

    public Post getPost(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post with given id not found"));
    }

    public Post editPost(Long id, Post post) {
        Post postFromDb = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post with given id not found"));
        postFromDb.setHeader(post.getHeader());
        postFromDb.setContent(post.getContent());
        postFromDb.setImage(post.getImage());
        return repository.save(postFromDb);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
