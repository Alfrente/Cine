package com.arroyo.cine.mapper.personaje;

import com.arroyo.cine.dto.personaje.PersonajeDto;
import com.arroyo.cine.entity.Personaje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonajeComplementoPMapper {

    @Mapping(target = "idePersonaje", source = "personaje.idPersonaje")
    @Mapping(target = "nombre", source = "personaje.nombre")
    @Mapping(target = "edad", source = "personaje.edad")
    @Mapping(target = "peso", source = "personaje.peso")
    @Mapping(target = "imagen", source = "personaje.imagen")
    @Mapping(target = "historia", source = "personaje.historia")
    @Mapping(target = "peliculaSeries", ignore = true)
    PersonajeDto aPersonajeDto(Personaje personaje);

    List<PersonajeDto> aListPersonajeDto(List<Personaje> personajes);
}