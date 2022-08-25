package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.repository.CommentRepository;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    CommentService service;
    Comment comment;
    Post post;
    User user;
    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .header("Testing")
                .build();
        user = new User(
                1L,
                "John",
                "john@o2.pl",
                "john123",
                "male",
                "avatar"
        );
    }

    @Test
    void givenCommentObject_whenSaveComment_thenReturnComment() {
        //given
        comment = Comment.builder()
                .id(1L)
                .author("John")
                .authorAvatar("avatar")
                .content("comment this shit")
                .date("1.01.1997")
                .post(post)
                .user(user)
                .build();
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        //when
        service.addCommentToPostByUser(post.getId(), user.getId(), comment);
        //then
        verify(commentRepository).save(any(Comment.class));
    }

    @Disabled
    @Test
    void getAllComments() {
    }

    @Disabled
    @Test
    void getCommentByUser() {
    }

    @Disabled
    @Test
    void updateCommentByUser() {
    }

    @Disabled
    @Test
    void deleteCommentByUser() {
    }
}