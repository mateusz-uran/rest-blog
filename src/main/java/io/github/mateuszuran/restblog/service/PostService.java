package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
    private final PostRepository repository;

    public PostService(final PostRepository repository) {
        this.repository = repository;
    }

    public Post addPost(Post newPost) {
        return repository.save(newPost);
    }

    public List<Post> getAllPosts() {
        return repository.findAll();
    }

    public Post getPost(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public Post editPost(Long id, Post newPost) {
        return repository.findById(id)
                .map(post -> {
                    post.updateForm(newPost);
                    return repository.save(post);
                })
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public Post updatePost(Long id, Post partialPost) {
        Post post = repository.findById(id).orElseThrow();
        if(repository.findById(id).isPresent()) {
            if(partialPost.getHeader() != null) {
                post.setHeader(partialPost.getHeader());
            }
            if(partialPost.getContent() != null) {
                post.setContent(partialPost.getContent());
            }
            if(partialPost.getImageName() != null) {
                post.setImageName(partialPost.getImageName());
            }
            if(partialPost.getImagePath() != null) {
                post.setImagePath(partialPost.getImagePath());
            }
            if(partialPost.getImageType() != null) {
                post.setImageType(partialPost.getImageType());
            }
        }
        return repository.save(post);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
