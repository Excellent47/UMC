package ru.mts.media.platform.umc.dao.postgres.venue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalIdInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.gql.types.VenueSlice;
import ru.mts.media.platform.umc.domain.venue.VenueSave;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class VenuePgDao implements VenueSot {
    private final VenuePgRepository repository;
    private final VenuePgMapper mapper = VenuePgMapper.INSTANCE;

    public Optional<Venue> getVenueByReferenceId(String id) {
        return Optional.of(id)
                .map(repository::findByReferenceId)
                .map(mapper::asModel);
    }

    @Override
    public Optional<Venue> getVenueById(FullExternalIdInput externalId) {
        Optional.of(externalId)
                .map(mapper::asPk)
                .flatMap(repository::findById);
        return Optional.empty();
    }

    @Override
    public List<Venue> getAllVenueByReferenceId(List<String> venueReferenceIds) {
        return repository.findAllByReferenceIdIn(venueReferenceIds)
                .stream()
                .map(mapper::asModelWithEvents)
                .collect(Collectors.toList());
    }

    @EventListener
    public void handleVenueCreatedEvent(VenueSave evt) {
        evt.unwrap()
                .map(mapper::asEntity)
                .ifPresent(repository::save);
    }

    @Override
    public VenueSlice getAllVenuesWithEvents(Integer page, Integer size) {
        var slice = repository.findAll(PageRequest.of(page, size))
                .map(mapper::asModelWithEvents);
        return new VenueSlice(
                slice.getContent(),
                slice.hasNext(),
                slice.hasPrevious(),
                slice.getNumber(),
                slice.getSize()
        );
    }
}
