package ru.mts.media.platform.umc.domain.venue;

import ru.mts.media.platform.umc.domain.gql.types.FullExternalIdInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.gql.types.VenueSlice;

import java.util.List;
import java.util.Optional;

public interface VenueSot {
    Optional<Venue> getVenueByReferenceId(String id);

    Optional<Venue> getVenueById(FullExternalIdInput externalId);

    List<Venue> getAllVenueByReferenceId(List<String> venueReferenceIds);

    VenueSlice getAllVenuesWithEvents(Integer page, Integer size);
}
