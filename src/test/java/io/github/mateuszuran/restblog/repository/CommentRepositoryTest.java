package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
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
    void givePostWithComments_whenFindComments_thenReturnList() {
        //when
        Post post = new Post(
                1L,
                "Header"
        );
        User user = new User(
                1L,
                "John",
                "john@gmail.com",
                "john123",
                "male",
                "avatar_john"
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
        PageRequest pageReq = PageRequest.of(0, 3);
        //when
        Page<Comment> commentsPaging = commentRepository.findAllByPostId(post.getId(), pageReq);
        List<Comment> comments = commentRepository.findAllByUserId(user.getId());
        //then
        assertThat(commentsPaging).isNotNull();
        assertThat(comments).isNotNull();
    }
}