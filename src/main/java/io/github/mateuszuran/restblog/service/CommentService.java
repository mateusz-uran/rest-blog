package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Comment;
import io.github.mateuszuran.restblog.model.Post;
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

    public Comment addComment(Long id, Comment comment) {
        Comment newComment = new Comment();
        newComment.setAuthor(comment.getAuthor());
        newComment.setContent(comment.getContent());
        newComment.setDate(comment.getDate());
        newComment.setPost(postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post with given id not found")));
        return repository.save(newComment);
    }

    public List<Comment> getAllComments(Long id) {
        return repository.findAllByPostId(id);
    }

    public Comment getCommentById(Long postId, Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment with given id not found"));
        return repository.findAllByPostId(postId)
                .stream()
                .filter(findComment -> comment.getId().equals(findComment.getId())).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Comment in this post doesn't exists"));
    }

    public void editComment(Long postId, Long commentId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post with given id not found"));
        Comment commentFromDb = repository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment with given id not found"));
        var result = post.getComments()
                .stream()
                .filter(findComment -> findComment.equals(commentFromDb)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Comment in this post doesn't exists"));
        result.setAuthor(comment.getAuthor());
        result.setContent(comment.getContent());
        result.setDate(comment.getDate());
        result.setPost(post);
        repository.save(result);
    }

    public void deleteCommentTest(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post with given id not found"));
        Comment commentToDelete = post.getComments()
                .stream()
                .filter(findComment -> findComment.getId().equals(commentId)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Comment with given id not found"));
        repository.deleteById(commentToDelete.getId());
    }

    public void deleteComment(Long commentId) {
        repository.deleteById(commentId);
    }
}
