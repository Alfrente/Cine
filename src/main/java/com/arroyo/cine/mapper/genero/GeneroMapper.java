package com.arroyo.cine.mapper.genero;

import com.arroyo.cine.model.dto.GeneroDto;
import com.arroyo.cine.model.entity.Genero;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneroMapper {
    GeneroDto aGeneroDto(Genero genero);
    List<GeneroDto> aListGeneroDto(List<Genero> generos);
    @InheritInverseConfiguration
    Genero aGenero(GeneroDto generoDto);
}
