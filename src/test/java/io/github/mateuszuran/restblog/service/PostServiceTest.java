package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceTest.class);
    @Mock
    PostRepository repository;

    PostService service;

    Post post;

    @DisplayName("Test for saving post")
    @Test
    void savedPost_success() {

        when(repository.save(any(Post.class))).thenReturn(post);

        Post savedPost = repository.save(post);
        assertThat(savedPost.getHeader()).isNotNull();
    }

    @DisplayName("Test if saved post exists in db")
    @Test
    void post_exists_in_db() {
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        when(repository.findAll()).thenReturn(postList);

        List<Post> fetchedPosts = service.getAllPosts();
        assertThat(fetchedPosts.size()).isGreaterThan(0);
    }
}