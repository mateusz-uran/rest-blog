package io.github.mateuszuran.restblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.Tags;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.TagsRepository;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class TagsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagsRepository repository;
    private Post post;

    private final String URL = "/api/v1";

    @BeforeEach
    void setUp() {
        post = new Post(1L, "Integration test");
        postRepository.save(post);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @WithMockUser(username = "spring", roles = "ADMIN")
    @Test
    void givenPostAndTagObject_whenPost_thenReturnObjectAndStatus200() throws Exception {
        //given
        Tags tag = new Tags(1L, "foo bar", post);
        //when
        mockMvc.perform(post(URL + "/add-tag")
                        .param("id", String.valueOf(post.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tag)))
                //then
                .andExpect(status().isOk());
        Tags result = repository.findById(1L).orElseThrow();
        assertThat(result.getContent()).isEqualTo("foo bar");
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void whenGetSingleTag_thenReturnStatus200andObject() throws Exception {
        //given
        Tags tag = new Tags(1L, "foo bar", post);
        repository.save(tag);
        //when
        mockMvc.perform(get(URL + "/tag")
                        .param("id", String.valueOf(post.getId()))
                        .param("tagId", String.valueOf(tag.getId())))
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "spring", roles = "ADMIN")
    @Test
    void givenSingleTag_whenUpdate_thenReturnUpdatedObjectAndStatus200() throws Exception {
        //given
        Tags tag = new Tags(1L, "foo bar", post);
        repository.save(tag);
        Tags newTag = new Tags();
        newTag.setContent("zzzz");
        //when
        mockMvc.perform(put(URL + "/edit-tag")
                        .param("id", String.valueOf(post.getId()))
                        .param("tagId", String.valueOf(tag.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTag)))
                //then
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content", is(newTag.getContent())));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "spring", roles = "ADMIN")
    @Test
    void givenTagId_whenDelete_thenReturnStatus200() throws Exception {
        //given
        Tags tag = new Tags(1L, "foo bar", post);
        repository.save(tag);
        //when
        mockMvc.perform(delete(URL + "/delete-tag")
                        .param("id", String.valueOf(post.getId()))
                        .param("tagId", String.valueOf(tag.getId())))
                //then
                .andExpect(status().isOk());
    }
}