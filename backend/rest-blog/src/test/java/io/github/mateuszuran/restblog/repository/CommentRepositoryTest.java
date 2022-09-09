package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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
                "foo",
                post,
                user
        );
        Comment comment2 = new Comment(
                2L,
                "author",
                "bar",
                post,
                user
        );
        postRepository.save(post);
        userRepository.save(user);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        //when
        List<Comment> comments = commentRepository.findAllByUserId(user.getId());
        //then
        assertThat(comments).isNotNull();
        assertThat(comments, hasSize(2));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void givePostWithComments_whenFindComments_thenReturnListWithPaging() {
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
                "foo",
                post,
                user
        );
        Comment comment2 = new Comment(
                2L,
                "author",
                "bar",
                post,
                user
        );
        postRepository.save(post);
        userRepository.save(user);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        PageRequest pageReq = PageRequest.of(0, 3);
        //when
        Page<Comment> commentsPaging = commentRepository.findAllByPostId(post.getId(), pageReq);
        //then
        assertThat(commentsPaging).isNotNull();
        assertEquals(commentsPaging.getTotalElements(), 2);
    }
}