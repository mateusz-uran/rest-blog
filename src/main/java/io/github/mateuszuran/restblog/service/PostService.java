package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.bucket.BucketName;
import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.filestore.FileStore;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
    private final PostRepository repository;
    private final FileStore fileStore;

    public PostService(final PostRepository repository, final FileStore fileStore) {
        this.repository = repository;
        this.fileStore = fileStore;
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
        if (repository.findById(id).isPresent()) {
            if (partialPost.getHeader() != null) {
                post.setHeader(partialPost.getHeader());
            }

            if (partialPost.getIntro() != null) {
                post.setIntro(partialPost.getIntro());
            }

            if (partialPost.getContent() != null) {
                post.setContent(partialPost.getContent());
            }
            if (partialPost.getProjectCodeLink() != null) {
                post.setProjectCodeLink(partialPost.getProjectCodeLink());
            }
            if (partialPost.getProjectDemoLink() != null) {
                post.setProjectDemoLink(partialPost.getProjectDemoLink());
            }
        }
        return repository.save(post);
    }

    public void deletePost(Long id) {
        if(repository.findById(id).orElseThrow(() -> new PostNotFoundException(id)) != null) {
            repository.deleteById(id);
        }
    }

    public void uploadImageToPost(final Long postId, final MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("File not found");
        }
        if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image");
        }
        if (repository.findById(postId).isEmpty()) {
            throw new PostNotFoundException(postId);
        }

        Map<String ,String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.POST_IMAGE.getBucketName(), postId);
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        Post post = repository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            post.setImageName(fileName);
            post.setImagePath(path);
            post.setImageType(file.getContentType());
            repository.save(post);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadPostImage(final Long postId) {
        Post post = repository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        String path = String.format("%s/%s",
                BucketName.POST_IMAGE.getBucketName(),
                postId);
        return fileStore.download(path, post.getImageName());
    }
}
