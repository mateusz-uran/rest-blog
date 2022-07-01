package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long id);

    Optional<Comment> findByPostId(Long id);
}
