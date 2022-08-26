package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.Tags;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TagsRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagsRepository tagsRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        tagsRepository.deleteAll();
    }

    @Test
    void givenPostId_whenGetFindAllByPostId_thenReturnListTags() {
        //given
        Post post = new Post(
                1L,
                "Header"
        );
        Tags tag1 = new Tags(
                1L,
                "foo",
                post
        );
        Tags tag2 = new Tags(
                2L,
                "bar",
                post
        );
        postRepository.save(post);
        tagsRepository.save(tag1);
        tagsRepository.save(tag2);
        //when
        var result = tagsRepository.findAllByPostId(post.getId());
        //then
        assertThat(result).isNotNull();
    }
}