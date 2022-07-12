package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    List<Tags> findAllByPostId(Long id);

    Optional<Tags> findByPostId(Long id);
}
