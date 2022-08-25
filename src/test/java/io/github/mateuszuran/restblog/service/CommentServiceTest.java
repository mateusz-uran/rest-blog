package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.repository.CommentRepository;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void givenCommentsList_whenGetAllComments_thenReturnCommentsList() {
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
        PageRequest pageReq = PageRequest.of(0, 3);
        when(commentRepository.findAllByPostId(post.getId(), pageReq)).thenReturn(new PageImpl<>(List.of(comment)));
        //when
        var result = service.getAllComments(post.getId(), pageReq.getPageNumber(), pageReq.getPageSize());
        var totalPages = commentRepository.findAllByPostId(post.getId(), pageReq).getTotalPages();

        Map<String, Object> comments = new HashMap<>();
        comments.put("totalPages", totalPages);
        comments.put("comment", comment);
        //then
        assertThat(result).isNotNull();
        assertTrue(result.containsKey("comments"));
        assertTrue(result.containsKey("totalPages"));
        assertThat(result, IsMapContaining.hasValue(totalPages));
    }

    @Disabled
    @Test
    void getCommentByUser() {
        //given
        //when
        //then
    }

    @Disabled
    @Test
    void updateCommentByUser() {
        //given
        //when
        //then
    }

    @Disabled
    @Test
    void deleteCommentByUser() {
        //given
        //when
        //then
    }
}