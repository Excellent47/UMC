package ru.mts.media.platform.umc.dao.postgres.venue;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.mts.media.platform.umc.dao.postgres.common.FullExternalIdPk;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgMapper;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalIdInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.stream.Collectors;

@Mapper
public interface VenuePgMapper {
    VenuePgMapper INSTANCE = Mappers.getMapper(VenuePgMapper.class);

    @Mapping(target = "externalId.brandId", source = "brand")
    @Mapping(target = "externalId.providerId", source = "provider")
    @Mapping(target = "externalId.externalId", source = "externalId")
    @Mapping(target = "id", source = "referenceId")
    @Mapping(target = "events", ignore = true)
    Venue asModel(VenuePgEntity venuePg);

    default Venue asModelWithEvents(VenuePgEntity venuePg) {
        Venue venue = asModel(venuePg);
        if (venuePg.getEvents() != null) {
            venue.setEvents(
                    venuePg.getEvents().stream()
                            .map(EventPgMapper.INSTANCE::asModel)
                            .collect(Collectors.toList())
            );
        }
        return venue;
    }

    @Mapping(target = "referenceId", source = "id")
    @Mapping(target = "brand", source = "externalId.brandId")
    @Mapping(target = "provider", source = "externalId.providerId")
    @Mapping(target = "externalId", source = "externalId.externalId")
    @Mapping(target = "events", ignore = true)
    VenuePgEntity asEntity(Venue venue);

    default VenuePgEntity asEntityWithEvents(Venue venue) {
        VenuePgEntity entity = asEntity(venue);
        if (venue.getEvents() != null) {
            entity.setEvents(
                    venue.getEvents().stream()
                            .map(EventPgMapper.INSTANCE::asEntity)
                            .collect(Collectors.toSet())
            );
        }
        return entity;
    }

    @Mapping(target = "brand", source = "brandId")
    @Mapping(target = "provider", source = "providerId")
    @Mapping(target = "externalId", source = "externalId")
    FullExternalIdPk asPk(FullExternalIdInput fullExternalId);
}
