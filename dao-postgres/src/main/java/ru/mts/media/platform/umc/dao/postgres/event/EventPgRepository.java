package ru.mts.media.platform.umc.dao.postgres.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventPgRepository extends JpaRepository<EventPgEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = "venues")
    Page<EventPgEntity> findAll(Pageable pageable);
} 