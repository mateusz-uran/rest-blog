package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.Tags;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.TagsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagsServiceTest {
    @InjectMocks
    TagsService service;
    Tags tag;
    Post post;
    @Mock
    TagsRepository tagsRepository;
    @Mock
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .header("Testing")
                .build();
    }

    @Test
    void givenTagObject_whenSaveTag_thenReturnTag() {
        //given
        tag = Tags.builder()
                .id(1L)
                .content("foo")
                .post(post)
                .build();
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        //when
        service.addTagToPost(post.getId(), tag);
        //then
        verify(tagsRepository).save(any(Tags.class));
    }

    @Test
    void givenTag_whenGetTagByIdFromPost_thenReturnTag() {
        //given
        tag = Tags.builder()
                .id(1L)
                .content("foo")
                .post(post)
                .build();
        //when
        when(tagsRepository.findAllByPostId(post.getId())).thenReturn(List.of(tag));
        var result = service.getTag(post.getId(), tag.getId());
        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(tag);
    }

    @Test
    void givenTagToUpdate_whenTagIsUpdated_thenReturnTagWithNewContent() {
        //given
        tag = Tags.builder()
                .id(1L)
                .content("foo")
                .post(post)
                .build();
        Tags tag2 = new Tags();
        tag2.setContent("bar");
        //when
        when(tagsRepository.findAllByPostId(post.getId())).thenReturn(List.of(tag));
        service.updateTagContent(post.getId(), tag.getId(), tag2);
        //then
        verify(tagsRepository).save(any(Tags.class));
        assertThat(tag.getContent()).isEqualTo("bar");
    }

    @Test
    void givenTag_whenDeleteTag_thenVerify() {
        //given
        tag = Tags.builder()
                .id(1L)
                .content("foo")
                .post(post)
                .build();
        //when
        when(tagsRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        when(tagsRepository.findAllByPostId(post.getId())).thenReturn(List.of(tag));
        service.deleteTag(post.getId(), tag.getId());
        //then
        verify(tagsRepository, times(1)).delete(tag);
    }
}