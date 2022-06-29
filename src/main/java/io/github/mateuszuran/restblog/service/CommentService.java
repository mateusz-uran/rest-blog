package io.github.mateuszuran.restblog.service;

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

    public Comment addComment(Long id, Comment comment) {
        Comment newComment = new Comment();
        newComment.setAuthor(comment.getAuthor());
        newComment.setContent(comment.getContent());
        newComment.setDate(comment.getDate());
        newComment.setPost(postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post with given id not found")));
        return repository.save(newComment);
    }

    public List<Comment> getAllComments(Long id) {
        return repository.findByPostId(id);
    }

    public Comment getCommentById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment with given id not found"));
    }

    public void editComment(Long id, Comment comment) {
        Comment commentFromDb = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment with given id not found"));
        commentFromDb.setAuthor(comment.getAuthor());
        commentFromDb.setContent(comment.getContent());
        commentFromDb.setDate(comment.getDate());
        commentFromDb.setPost(comment.getPost());
        repository.save(commentFromDb);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
