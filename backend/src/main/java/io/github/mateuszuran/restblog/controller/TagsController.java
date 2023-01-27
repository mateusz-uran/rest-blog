package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.model.Tags;
import io.github.mateuszuran.restblog.service.TagsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:9090/")
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
}
