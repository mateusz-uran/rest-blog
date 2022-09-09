package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.IncorrectUserIdException;
import io.github.mateuszuran.restblog.exception.PostNotFoundException;
import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.repository.CommentRepository;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Comment addCommentToPostByUser(Long id, Long userId, Comment comment) {
        Comment newComment = new Comment();
        newComment.toUpdate(comment);
        newComment.setPost(getPostById(id));
        newComment.setUser(getUserById(userId));
        return repository.save(newComment);
    }

    public Map<String, Object> getAllComments(Long id, int page, int size) {
        PageRequest pageReq = PageRequest.of(page, size);
        var commentsPage = repository.findAllByPostId(id, pageReq);
        var totalPages = commentsPage.getTotalPages();
        List<Comment> comments = commentsPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("totalPages", totalPages);
        response.put("comments", comments);
        return response;
    }

    public Comment getCommentByUser(Long id, Long commentId, Long userId) {
        return findCommentsByUser(id, commentId, userId);
    }

    public Comment updateCommentByUser(Long id, Long commentId, Long userId, Comment update) {
        var result = findCommentsByUser(id, commentId, userId);
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
                .orElseThrow(() -> new IncorrectUserIdException(userId));
    }

    private User getUserById(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
    }

    private Post getPostById(final Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }
}
