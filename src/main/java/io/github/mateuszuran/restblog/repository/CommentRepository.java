package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
