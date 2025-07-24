package ru.mts.media.platform.umc.dao.postgres.event;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.domain.event.EventSot;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.EventSlice;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventPgDao implements EventSot {

    @PersistenceContext
    private final EntityManager entityManager;
    private final EventPgRepository repository;
    private final EventPgMapper mapper = EventPgMapper.INSTANCE;

    @Transactional
    @Override
    public Optional<Event> createEvent(Event event) {
        EventPgEntity entityWithVenues = mapper.asEntityWithVenues(event);
        EventPgEntity merge = entityManager.merge(entityWithVenues);
        return Optional.of(mapper.asModelWithVenues(merge));
    }

    @Override
    public EventSlice getAllEventsWithVenues(int pageNumber, int pageSize) {
        var slice = repository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(mapper::asModelWithVenues);
        return new EventSlice(
                slice.getContent(),
                slice.hasNext(),
                slice.hasPrevious(),
                slice.getNumber(),
                slice.getSize()
        );
    }
} 