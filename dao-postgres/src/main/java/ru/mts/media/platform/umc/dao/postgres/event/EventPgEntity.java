package ru.mts.media.platform.umc.dao.postgres.event;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgEntity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "event",
        indexes = {
                @Index(name = "idx_event_start_time", columnList = "start_time"),
                @Index(name = "idx_event_end_time", columnList = "end_time")
        })
public class EventPgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_venue",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "venue_reference_id", referencedColumnName = "referenceId")
    )
    private Set<VenuePgEntity> venues;
}