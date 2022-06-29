package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
