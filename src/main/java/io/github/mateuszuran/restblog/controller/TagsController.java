package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Tags;
import io.github.mateuszuran.restblog.service.TagsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/post/")
public class TagsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagsController.class);
    private final TagsService service;

    public TagsController(final TagsService service) {
        this.service = service;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/tags")
    public List<Tags> getAllTagsInPost(@PathVariable("id") Long id) {
        return service.getAllTagsByPostId(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/tag/{tagId}")
    public Tags getTag(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId) {
        return service.getTagByPostId(id, tagId);
    }

    @PostMapping("/{id}/add-tag")
    public Tags addTagToPost(@PathVariable("id") Long id, @RequestBody Tags newTag) {
        return service.addTagToPost(id, newTag);
    }

    @PutMapping("/{id}/edit-tag/{tagId}")
    public Tags editTag(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId, @RequestBody Tags toUpdate) {
        return service.updateTag(id, tagId, toUpdate);
    }

    @DeleteMapping("/{id}/delete-tag/{tagId}")
    public void deleteTag(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId) {
        service.deleteTag(id, tagId);
    }
}
