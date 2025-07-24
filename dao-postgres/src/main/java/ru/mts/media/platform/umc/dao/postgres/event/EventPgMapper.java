package ru.mts.media.platform.umc.dao.postgres.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgMapper;
import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface EventPgMapper {
    EventPgMapper INSTANCE = Mappers.getMapper(EventPgMapper.class);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToString")
    @Mapping(target = "venues", ignore = true)
    Event asModel(EventPgEntity entity);

    default Event asModelWithVenues(EventPgEntity entity) {
        Event event = asModel(entity);
        if (entity.getVenues() != null) {
            event.setVenues(
                    entity.getVenues()
                            .stream()
                            .map(VenuePgMapper.INSTANCE::asModel)
                            .collect(Collectors.toList())
            );
        }
        return event;
    }

    @Mapping(target = "id", source = "id", qualifiedByName = "stringToUuid")
    @Mapping(target = "venues", ignore = true)
    EventPgEntity asEntity(Event event);

    default EventPgEntity asEntityWithVenues(Event event) {
        EventPgEntity entity = asEntity(event);
        if (event.getVenues() != null) {
            entity.setVenues(
                    event.getVenues()
                            .stream()
                            .map(VenuePgMapper.INSTANCE::asEntity)
                            .collect(Collectors.toSet())
            );
        }
        return entity;
    }

    @Named("stringToUuid")
    default UUID stringToUuid(String uuid) {
        return uuid != null ? UUID.fromString(uuid) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }
} 