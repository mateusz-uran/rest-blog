package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.PostImage;
import io.github.mateuszuran.restblog.repository.PostImageRepository;
import io.github.mateuszuran.restblog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;
    private final PostImageRepository imageRepository;

    public PostService(final PostRepository repository, final PostImageRepository imageRepository) {
        this.repository = repository;
        this.imageRepository = imageRepository;
    }

    public Post addPost(Post newPost) {
        return repository.save(newPost);
    }

    public List<Post> getAllPosts() {
        return  repository.findAll();
    }

    public Post getPost(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public Post editPost(Long id, Post newPost) {
        PostImage image = imageRepository.findByPostId(id);
        return repository.findById(id)
                .map(post -> {
                    post.updateForm(newPost);
                    post.setImage(image);
                    return repository.save(post);
                })
                .orElseGet(() -> {
                    newPost.setId(id);
                    return repository.save(newPost);
                });
        }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
