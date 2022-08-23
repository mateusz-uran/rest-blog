package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void givePostWithComments_whenFindCommentsByPostId_thenReturnPageableList() {
        //when
        Post post = new Post(
                1L,
                "Header"
        );
        Comment comment = new Comment(
                1L,
                "author",
                post,
                null
        );
        postRepository.save(post);
        commentRepository.save(comment);
        PageRequest pageReq = PageRequest.of(0, 3);
        //when
        Page<Comment> comments = commentRepository.findAllByPostId(post.getId(), pageReq);
        //then
        assertThat(comments).isNotNull();
    }

    @Test
    void givePostWithUserAndComments_whenFindCommentsById_thenReturnListOfComments() {
        //given
        Post post = new Post(
                1L,
                "Header"
        );
        User user = new User(
                1L,
                "username",
                "email",
                "password",
                "gender",
                "avatar"
        );
        Comment comment = new Comment(
                1L,
                "author",
                post,
                user
        );
        postRepository.save(post);
        userRepository.save(user);
        commentRepository.save(comment);
        //when
        List<Comment> comments = commentRepository.findAllByUserId(user.getId());
        //then
        assertThat(comments).isNotNull();
    }
}