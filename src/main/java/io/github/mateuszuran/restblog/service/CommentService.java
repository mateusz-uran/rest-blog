package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.CommentNotFoundException;
import io.github.mateuszuran.restblog.exception.IncorrectUserIdException;
import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.repository.CommentRepository;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentService {
    private final CommentRepository repository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(final CommentRepository repository, final PostRepository postRepository, final UserRepository userRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment addCommentToPost(Long id, Comment comment) {
        Comment newComment = new Comment();
        newComment.toUpdate(comment);
        newComment.setPost(postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id)));
        return repository.save(newComment);
    }

    public Comment addCommentToPostByUser(Long id, Long userId, Comment comment) {
        Comment newComment = new Comment();
        newComment.toUpdate(comment);
        newComment.setPost(postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id)));
        newComment.setUser(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with given id not found")));
        return repository.save(newComment);
    }

    public List<Comment> getAllCommentsByPostId(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        return repository.findAllByPostId(id);
    }

    public Comment getCommentByPostId(Long postId, Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        return findCommentsInPost(postId, commentId, comment);
    }

    public Comment getCommentByUser(Long id, Long postId, Long commentId) {
        return findCommentsByUser(id, postId, commentId);
    }

    public Comment updateComment(Long postId, Long commentId, Comment update) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        var result = findCommentsInPost(postId, commentId, comment);
        result.toUpdate(update);
        return repository.save(result);
    }

    public Comment updateCommentByUser(Long id, Long postId, Long commentId, Comment update) {
        var result = findCommentsByUser(id, postId, commentId);
        result.toUpdate(update);
        return repository.save(result);
    }

    public void deleteCommentByUser(Long id, Long commentId, Long userId) {
        var result = findCommentsByUser(id, commentId, userId);
        repository.delete(result);
    }

    private Comment findCommentsByUser(Long id, Long commentId, Long userId) {
        var result = repository.findAllByUserId(userId);
        return result.stream()
                .filter(comment -> id.equals(comment.getPost().getId())
                        && commentId.equals(comment.getId())
                        && userId.equals(comment.getUser().getId())).findAny()
                .orElseThrow(() -> new IncorrectUserIdException(id));
    }

    private Comment findCommentsInPost(final Long postId, final Long commentId, final Comment comment) {
        return repository.findAllByPostId(postId)
                .stream()
                .filter(findComment -> comment.getId().equals(findComment.getId())).findAny()
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }
}
