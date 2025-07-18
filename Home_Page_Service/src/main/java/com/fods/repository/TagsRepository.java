package com.fods.repository;

import com.fods.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long> {
}
