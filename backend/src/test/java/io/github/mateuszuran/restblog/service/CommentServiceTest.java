package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.IncorrectUserIdException;
import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.repository.CommentRepository;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import io.github.mateuszuran.restblog.service.CommentService;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void givenCommentObject_whenSaveComment_thenReturnPostNotFound() {
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
        given(postRepository.findById(post.getId())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> service.addCommentToPostByUser(post.getId(), user.getId(), comment))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessageContaining("Post with id: " + post.getId() + " not found");
    }

    @Test
    void givenCommentObject_whenSaveComment_thenThrowException() {
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
        given(userRepository.findById(user.getId())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> service.addCommentToPostByUser(post.getId(), user.getId(), comment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User with given id not found");
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
        //then
        assertThat(result).isNotNull();
        assertTrue(result.containsKey("comments"));
        assertTrue(result.containsKey("totalPages"));
        assertThat(result, IsMapContaining.hasValue(totalPages));
    }

    @Test
    void givenPostUserCommentId_whenGetAllCommentsByUserAndGetComment_thenGetComment() {
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
        //when
        when(commentRepository.findAllByUserId(user.getId())).thenReturn(List.of(comment));
        var result = service.getCommentByUser(post.getId(), comment.getId(), user.getId());
        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(comment);
    }

    @Test
    void givenPostUserCommentId_whenGetAllCommentsByUserAndGetComment_thenThrowException() {
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
        //when
        //then
        Throwable exception = assertThrows(IncorrectUserIdException.class, () ->
                service.getCommentByUser(post.getId(), comment.getId(), user.getId()));
        assertEquals("User with id: " + user.getId() + " is unauthorized", exception.getMessage());
    }

    @Test
    void givenComment_whenGetAllCommentsByUserAndUpdate_thenReturnUpdatedComment() {
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
        Comment comment2 = new Comment();
        comment2.setContent("updated");
        //when
        when(commentRepository.findAllByUserId(user.getId())).thenReturn(List.of(comment));
        service.updateCommentByUser(post.getId(), comment.getId(), user.getId(), comment2);
        //then
        verify(commentRepository).save(any(Comment.class));
        assertThat(comment.getContent()).isEqualTo("updated");
    }

    @Test
    void givenComment_whenGetAllCommentsByUserAndUpdate_thenThrowException() {
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
        //when
        //then
        Throwable exception = assertThrows(IncorrectUserIdException.class, () ->
                service.updateCommentByUser(post.getId(), comment.getId(), user.getId(), comment));
        assertEquals("User with id: " + user.getId() + " is unauthorized", exception.getMessage());
    }

    @Test
    void givenComment_whenDelete_thenVerify() {
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
        //when
        when(commentRepository.findAllByUserId(user.getId())).thenReturn(List.of(comment));
        service.deleteCommentByUser(post.getId(), comment.getId(), user.getId());
        //then
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void givenComment_whenDelete_thenThrowException() {
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
        //when
        //then
        Throwable exception = assertThrows(IncorrectUserIdException.class, () ->
                service.deleteCommentByUser(post.getId(), comment.getId(), user.getId()));
        assertEquals("User with id: " + user.getId() + " is unauthorized", exception.getMessage());
    }
}