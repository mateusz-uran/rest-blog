package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceTest.class);
    @Mock
    PostRepository repository;

    PostService service;

    Post post;

    @BeforeEach
    void initUseCase() {
        post = Post.builder()
                .id(1L)
                .header("Title")
                .content("Description")
                .imagePath("/src/bucket")
                .imageName("img")
                .imageType("jpg")
                .build();
    }

    @DisplayName("Test for saving post")
    @Test
    void when_save_post_it_should_return_post() {
        when(repository.save(post)).thenReturn(post);
        Post savedPost = repository.save(post);
        assertThat(savedPost.getHeader()).isNotNull();
    }
}