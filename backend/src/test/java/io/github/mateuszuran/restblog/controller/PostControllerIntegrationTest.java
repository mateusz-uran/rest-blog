package io.github.mateuszuran.restblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository repository;

    private final String URL = "/api/v1/post";

    @WithMockUser(username = "spring", roles = "ADMIN")
    @Test
    void givenPostObject_whenPost_thenReturn200andObject() throws Exception {
        //given
        Post post = new Post(1L, "Integration test");
        //when
        mockMvc.perform(post(URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk());
        //then
        Post result = repository.findById(1L).orElseThrow();
        assertThat(result.getHeader()).isEqualTo("Integration test");
    }

    @Test
    void whenGet_thenReturn200isOk() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get(URL + "/all")).andExpect(status().isOk());
    }

    @Test
    void whenGetSingle_thenReturn200andObjectBody() throws Exception {
        //given
        Post post = new Post(1L, "Integration test");
        repository.save(post);
        //when + then
        mockMvc.perform(get(URL + "/single")
                        .param("id", String.valueOf(post.getId()))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @WithMockUser(username = "spring", roles = "ADMIN")
    @Test
    void whenGetSingle_thenUpdateAndExpect200adnReturnObjectBody() throws Exception {
        //given
        Post post = new Post(1L, "Integration test");
        repository.save(post);
        Post newPost = new Post();
        newPost.setHeader("Integration test update");
        //when
        mockMvc.perform(put(URL + "/update")
                        .param("id", String.valueOf(post.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPost)))
                //then
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.header", is(newPost.getHeader())));
    }

    @WithMockUser(username = "spring", roles = "ADMIN")
    @Test
    void whenGetObject_thenDeleteAndExpect200() throws Exception {
        //given
        Post post = new Post(1L, "Integration test");
        repository.save(post);
        //when
        mockMvc.perform(delete(URL + "/delete-post")
                        .param("id", String.valueOf(post.getId())))
                //then
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "spring", roles = "ADMIN")
    @Test
    void givenPostImage_whenPost_thenReturn200isOk() throws Exception {
        //given
        Post post = new Post(1L, "Integration test");
        repository.save(post);
        MockMultipartFile file = new MockMultipartFile("file", "image.png", "image/png", "data".getBytes());
        //when
        mockMvc.perform(multipart(URL + "/upload").file(file)
                        .param("postId", String.valueOf(post.getId())))
                //then
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "spring", roles = "ADMIN")
    @Test
    void givenPostId_whenGet_thenReturnImage() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("file", "image.png", "image/png", "data".getBytes());
        Post post = new Post(1L, "Integration test");
        repository.save(post);
        mockMvc.perform(multipart(URL + "/upload").file(file)
                .param("postId", String.valueOf(post.getId())));
        //when
        mockMvc.perform(get(URL + "/" + post.getId() + "/download"))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(content().bytes(file.getBytes()));
    }
}