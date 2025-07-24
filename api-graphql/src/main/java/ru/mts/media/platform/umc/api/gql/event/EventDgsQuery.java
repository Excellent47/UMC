package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.event.EventSot;
import ru.mts.media.platform.umc.domain.gql.types.EventSlice;

@DgsComponent
@RequiredArgsConstructor
public class EventDgsQuery {

    private final EventSot sot;

    @DgsQuery
    public EventSlice events(@InputArgument Integer page, @InputArgument Integer size) {
        return sot.getAllEventsWithVenues(page, size);
    }
} 