package com.catalogue.musique.mapper;

import com.catalogue.musique.dto.reponse.ChansonResponce;
import com.catalogue.musique.dto.request.ChansonRequest;
import com.catalogue.musique.model.Chanson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface ChansonMapper {

    ChansonMapper INSTANCE = Mappers.getMapper(ChansonMapper.class);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "album", ignore = true)
    Chanson toEntity(ChansonRequest chansonRequest);

    ChansonResponce toResponse(Chanson chanson);
}
