package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    List<Tags> findAllByPostId(Long id);
}
