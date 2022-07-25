package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Tags;
import io.github.mateuszuran.restblog.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/")
public class TagsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagsController.class);
    private final TagsService service;

    public TagsController(final TagsService service) {
        this.service = service;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/tags")
    public List<Tags> getAllTagsInPostByParam(@RequestParam Long id) {
        return service.getAllTagsByPostId(id);
    }

    @GetMapping("/{id}/tag/{tagId}")
    public Tags getTag(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId) {
        return service.getTagByPostId(id, tagId);
    }

    @PutMapping("/{id}/edit-tag/{tagId}")
    public Tags editTag(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId, @RequestBody Tags toUpdate) {
        return service.updateTag(id, tagId, toUpdate);
    }

    @GetMapping("/tag")
    public Tags getTagByParam(@RequestParam Long id, @RequestParam Long tagId) {
        return service.getTag(id, tagId);
    }

    @PostMapping("/add-tag")
    public Tags addTagToPostByParam(@RequestParam Long id, @RequestBody Tags newTag) {
        return service.addTagToPost(id, newTag);
    }

    @PutMapping("/edit-tag")
    public Tags editTagByParam(@RequestParam Long id, @RequestParam Long tagId, @RequestBody Tags toUpdate) {
        return service.updateTagContent(id, tagId, toUpdate);
    }

    @DeleteMapping("/delete-tag")
    public void deleteTagByParam(@RequestParam Long id, @RequestParam Long tagId) {
        service.deleteTag(id, tagId);
    }

    @GetMapping("/test")
    public void testTokenRefreshForAdmin() {
        log.info("Secured data by token available only for admin");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/test/all")
    public void testTokenRefreshForAll() {
        log.info("Not secured data");
    }
}
