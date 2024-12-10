package com.catalogue.musique.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.catalogue.musique.dto.reponse.AlbumResponce;
import com.catalogue.musique.dto.request.AlbumRequest;
import com.catalogue.musique.model.Album;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumMapper INSTANCE= Mappers.getMapper(AlbumMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chansons", ignore = true)
    Album toEntity(AlbumRequest albumRequest);

    @Mapping(target = "chansons", source = "chansons")
    AlbumResponce toResponse(Album album);
}
