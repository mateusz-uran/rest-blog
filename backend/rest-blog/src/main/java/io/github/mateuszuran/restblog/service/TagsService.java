package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.exception.TagNotFoundException;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.Tags;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.TagsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class TagsService {
    private final TagsRepository repository;
    private final PostRepository postRepository;

    public TagsService(final TagsRepository repository, final PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    public Tags addTagToPost(Long id, Tags tag) {
        Tags newTag = new Tags();
        newTag.toUpdate(tag);
        newTag.setPost(postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id)));
        return repository.save(newTag);
    }

    public Tags getTag(Long postId, Long tagId) {
        return findTagInPost(postId, tagId);
    }

    public Tags updateTagContent(Long postId, Long tagId, Tags tagToUpdate) {
        Tags tagFromDb = findTagInPost(postId, tagId);
        tagFromDb.setContent(tagToUpdate.getContent());
        return repository.save(tagFromDb);
    }

    public void deleteTag(Long postId, Long tagId) {
        var result = findTagInPost(postId, tagId);
        repository.deleteById(result.getId());
    }

    private Tags findTagInPost(Long postId, Long tagId) {
        return repository.findAllByPostId(postId)
                .stream().filter(tag ->
                        tag.getId().equals(tagId)).findFirst()
                .orElseThrow(() -> new TagNotFoundException(tagId));
    }
}
