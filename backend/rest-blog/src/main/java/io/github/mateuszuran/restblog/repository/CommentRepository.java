package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostId(Long id, Pageable page);

    List<Comment> findAllByUserId(Long id);
}
