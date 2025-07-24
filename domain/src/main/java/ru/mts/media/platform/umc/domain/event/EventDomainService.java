package ru.mts.media.platform.umc.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.mts.media.platform.umc.domain.common.EntityEvent;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.SaveEventInput;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

@Service
@RequiredArgsConstructor
public class EventDomainService {

    private final ApplicationEventPublisher eventPublisher;
    private final EventSot eventSot;
    private final VenueSot venueSot;
    private final EventDomainServiceMapper mapper;

    public Event save(SaveEventInput input) {
        Event event = mapper.patch(new Event(), input);
        event.setVenues(venueSot.getAllVenueByReferenceId(input.getVenueReferenceIds()));

        var evt = eventSot.createEvent(event).map(EventSave::new);
        evt.ifPresent(eventPublisher::publishEvent);

        return evt.map(EntityEvent::getEntity).orElse(null);
    }


}