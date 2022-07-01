package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.CommentNotFoundException;
import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.repository.CommentRepository;
import io.github.mateuszuran.restblog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository repository;
    private final PostRepository postRepository;

    public CommentService(final CommentRepository repository, final PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    public Comment addCommentToPost(Long id, Comment comment) {
        Comment newComment = new Comment();
        newComment.toUpdate(comment);
        newComment.setPost(postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id)));
        return repository.save(newComment);
    }

    public List<Comment> getAllCommentsByPostId(Long id) {
        if(!postRepository.existsById(id)) {
            throw  new PostNotFoundException(id);
        }
        return repository.findAllByPostId(id);
    }

    public Comment getCommentByPostId(Long postId, Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        return findCommentsInPost(postId, commentId, comment);
    }

    public Comment updateComment(Long postId, Long commentId, Comment update) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        var result = findCommentsInPost(postId, commentId, comment);
        result.toUpdate(update);
        return repository.save(result);
    }

    public void deleteComment(Long postId, Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        var result = findCommentsInPost(postId, commentId, comment);
        repository.delete(result);
    }

    private Comment findCommentsInPost(final Long postId, final Long commentId, final Comment comment) {
        return repository.findAllByPostId(postId)
                .stream()
                .filter(findComment -> comment.getId().equals(findComment.getId())).findAny()
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }
}
