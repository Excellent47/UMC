package ru.mts.media.platform.umc.domain.event;

import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.EventSlice;

import java.util.Optional;

public interface EventSot {

    Optional<Event> createEvent(Event event);

    EventSlice getAllEventsWithVenues(int pageNumber, int pageSize);
}