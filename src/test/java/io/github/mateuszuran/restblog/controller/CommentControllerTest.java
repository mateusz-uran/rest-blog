package io.github.mateuszuran.restblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.repository.CommentRepository;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository repository;
    private Post post;
    private User user;
    private Comment comment;

    private final String URL = "/api/v1/post";

    @BeforeEach
    void setUp() {
        post = new Post(1L, "Integration test");
        postRepository.save(post);
        user = new User(1L, "John", "john@gmail.com", "john123", "male", "avatar");
        userRepository.save(user);
        comment = new Comment(1L, "John", "foo bar", post, user);
        repository.save(comment);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getAllComments() throws Exception {
        //given
        Comment commentToList = new Comment(2L, "John", "testing", post, user);
        repository.save(commentToList);
        //when
        mockMvc.perform(get(URL + "/comments-page-map")
                        .param("id", String.valueOf(post.getId()))
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(3)))
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getCommentByParamAndUser() throws Exception {
        //given
        //when
        mockMvc.perform(get(URL + "/comment-by-user")
                        .param("id", String.valueOf(post.getId()))
                        .param("commentId", String.valueOf(comment.getId()))
                        .param("userId", String.valueOf(user.getId())))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is(comment.getContent())));
    }

    @WithMockUser(username = "spring", roles = "USER")
    @Test
    void addCommentToPostByParamAndUser() throws Exception {
        //given
        Comment newComment = new Comment(2L, "John", "testing", post, user);
        //when
        mockMvc.perform(post(URL + "/add-comment-by-user")
                        .param("id", String.valueOf(post.getId()))
                        .param("userId", String.valueOf(user.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newComment)))
                .andExpect(status().isOk());
        //then
        Comment result = repository.findById(2L).orElseThrow();
        assertThat(result.getContent()).isEqualTo("testing");
    }

    @WithMockUser(username = "spring", roles = "USER")
    @Test
    void editCommentByParamAndUser() throws Exception {
        //given
        Comment newComment = new Comment();
        newComment.setContent("testing");
        //when
        mockMvc.perform(put(URL + "/edit-comment-by-user")
                        .param("id", String.valueOf(post.getId()))
                        .param("userId", String.valueOf(user.getId()))
                        .param("commentId", String.valueOf(comment.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newComment)))
                .andExpect(status().isOk());
        //then
        Comment result = repository.findById(1L).orElseThrow();
        assertThat(result.getContent()).isEqualTo("testing");
    }

    @WithMockUser(username = "spring", roles = "USER")
    @Test
    void deleteCommentByUsername() throws Exception {
        //given
        //when
        mockMvc.perform(delete(URL + "/delete-comment-by-user")
                        .param("id", String.valueOf(post.getId()))
                        .param("userId", String.valueOf(user.getId()))
                        .param("commentId", String.valueOf(comment.getId())))
                //then
                .andExpect(status().isOk());
    }
}