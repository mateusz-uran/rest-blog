package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.CommentNotFoundException;
import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.exception.TagNotFoundException;
import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Tags;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.TagsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Tags> getAllTagsByPostId(Long id) {
        if(!postRepository.existsById(id)) {
            throw  new PostNotFoundException(id);
        }
        return repository.findAllByPostId(id);
    }

    public Tags getTagByPostId(Long postId, Long tagId) {
        Tags tag = repository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
        return findTagsInPost(postId, tagId, tag);
    }

    public Tags updateTag(Long postId, Long tagId, Tags update) {
        Tags tag = repository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
        var result = findTagsInPost(postId, tagId, tag);
        result.toUpdate(update);
        return repository.save(result);
    }

    public void deleteTag(Long postId, Long tagId) {
        Tags tag = repository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
        var result = findTagsInPost(postId, tagId, tag);
        repository.delete(result);
    }

    private Tags findTagsInPost(final Long postId, final Long tagId, final Tags tag) {
        return repository.findAllByPostId(postId)
                .stream()
                .filter(findComment -> tag.getId().equals(findComment.getId())).findAny()
                .orElseThrow(() -> new TagNotFoundException(tagId));
    }
}
