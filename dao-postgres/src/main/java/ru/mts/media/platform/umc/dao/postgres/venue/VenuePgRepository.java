package ru.mts.media.platform.umc.dao.postgres.venue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.media.platform.umc.dao.postgres.common.FullExternalIdPk;

import java.util.List;

@Repository
interface VenuePgRepository extends JpaRepository<VenuePgEntity, FullExternalIdPk> {

    VenuePgEntity findByReferenceId(String referenceId);

    @EntityGraph(attributePaths = "events")
    List<VenuePgEntity> findAllByReferenceIdIn(List<String> referenceId);

    @Override
    @EntityGraph(attributePaths = "events")
    Page<VenuePgEntity> findAll(Pageable pageable);
}
