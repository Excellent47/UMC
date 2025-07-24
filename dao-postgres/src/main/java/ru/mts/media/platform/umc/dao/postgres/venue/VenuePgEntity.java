package ru.mts.media.platform.umc.dao.postgres.venue;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.mts.media.platform.umc.dao.postgres.common.FullExternalIdPk;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgEntity;

import java.util.Set;

@Entity
@Data
@IdClass(FullExternalIdPk.class)
@Table(name = "venue",
        indexes = {
                @Index(name = "idx_venue_referenceId",
                        columnList = "referenceId",
                        unique = true)
        })
public class VenuePgEntity {
    @Id
    private String brand;

    @Id
    private String provider;

    @Id
    private String externalId;

    private String referenceId;

    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "venues", fetch = FetchType.LAZY)
    @OrderBy("startTime DESC")
    private Set<EventPgEntity> events;
}
