package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository repository;
    @InjectMocks
    private PostService service;

    private Post post;

    @BeforeEach
    public void setup() {
        post = Post.builder()
                .id(1L)
                .header("Unit test")
                .intro("testing with mockito")
                .content("foo bar")
                .imageName("img")
                .imagePath("img path")
                .imageType("img type")
                .projectCodeLink("code link")
                .projectDemoLink("demo link")
                .build();
    }

    @Test
    public void givenPostObject_whenSavePost_thenReturnPost_Object() {
        //given
        given(repository.save(post)).willReturn(post);
        //when
        Post savePost = service.addPost(post);
        //then
        assertThat(savePost).isNotNull();
    }

    @Test
    public void givenPostList_whenGetAllPosts_thenReturnPostList() {
        //given
        Post post1 = Post.builder()
                .id(2L)
                .header("Unit test list")
                .intro("testing with mockito list")
                .content("foo bar list")
                .imageName("img list")
                .imagePath("img path list")
                .imageType("img type list")
                .projectCodeLink("code link list")
                .projectDemoLink("demo link list")
                .build();
        given(repository.findAll()).willReturn(List.of(post, post1));
        //when
        List<Post> postList = service.getAllPosts();
        //then
        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    @Test
    public void givenPostId_whenGetPostById_thenReturnPostObject() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(post));
        //when
        Post savedPost = service.getPost(post.getId());
        //then
        assertThat(savedPost).isNotNull();
        assertThat(post.getHeader()).isEqualTo("Unit test");
    }

    @Test
    public void givenPostObject_whenEditPost_thenReturnEditedPost() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(post));
        given(repository.save(post)).willReturn(post);
        Post newPost = new Post();
        newPost.setHeader("Unit test update");
        //when
        Post editedPost = service.editPost(1L, newPost);
        //then
        assertThat(editedPost.getHeader()).isEqualTo("Unit test update");
    }

    @Test
    public void givenPostId_whenDeletePost_thenNothing() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(post));
        willDoNothing().given(repository).deleteById(1L);
        //when
        service.deletePost(1L);
        //then
        verify(repository, times(1)).deleteById(1L);
    }
}