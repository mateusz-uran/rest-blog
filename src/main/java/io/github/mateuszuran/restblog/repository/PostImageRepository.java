package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    PostImage findByPostId(Long id);
}
