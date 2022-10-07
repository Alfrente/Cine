package com.arroyo.cine.service;

import com.arroyo.cine.dto.genero.GeneroDto;
import com.arroyo.cine.entity.Genero;
import com.arroyo.cine.exception.custom.genero.GeneroExcepcion;
import com.arroyo.cine.exception.custom.genero.GeneroExcepciones;
import com.arroyo.cine.mapper.genero.GeneroMapper;
import com.arroyo.cine.repository.GeneroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.arroyo.cine.util.statico.ExprecionRegular.EXPRECION_NOMBRE;
import static com.arroyo.cine.util.statico.RespuestaExcepcion.*;

@Service
public class GeneroService {
    private final GeneroRepository repository;
    private final GeneroMapper mapper;
    private Genero genero;
    private boolean hayError;

    public GeneroService(GeneroRepository repository, GeneroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<GeneroDto> getAll() {
        return mapper.aListGeneroDto(repository.findAll());
    }

    public GeneroDto getById(Integer idGenero) {
        return mapper.aGeneroDto(buscarConId(idGenero));
    }

    @Transactional
    public GeneroDto save(@Valid @NotNull GeneroDto dto) {
        validarEntradaNombreGenero(dto.getNombreGenero());
        return mapper.aGeneroDto(repository.save(mapper.aGenero(dto)));
    }

    @Transactional
    public GeneroDto update(Integer idGenero, String nuevoGenero) {
        this.genero = null;
        this.genero = buscarConId(idGenero);
        validarEntradaNombreGenero(nuevoGenero);
        this.genero.setNombre(nuevoGenero);
        return mapper.aGeneroDto(repository.save(genero));
    }

    @Transactional
    public GeneroDto delete(@Valid @NotNull GeneroDto dto) {
        this.genero = null;
        validarIdGenero(dto.getIdeGenero());
        this.genero = buscarConId(dto.getIdeGenero());
        validarGeneroDtoEliminar(dto, genero);
        repository.delete(mapper.aGenero(dto));
        return mapper.aGeneroDto(this.genero);
    }

    @Transactional
    public GeneroDto deleteById(Integer idGenero) {
        this.genero = null;
        this.genero = buscarConId(idGenero);
        repository.deleteById(this.genero.getIdGenero());
        return mapper.aGeneroDto(this.genero);
    }

    private Genero buscarConId(Integer idGenero) {
        return repository.findById(idGenero).
                orElseThrow(() -> new GeneroExcepcion(ID_GENERO_NO_DISPONIBLE, HttpStatus.BAD_REQUEST));
    }

    private void validarIdGenero(Integer idGenero) {
        if (idGenero == null || idGenero <= 0)
            throw new GeneroExcepcion(ID_GENERO_NO_INGRESADO, HttpStatus.BAD_REQUEST);
    }

    private void validarGeneroDtoEliminar(GeneroDto dto, Genero genero) {
        this.hayError = false;
        List<String> excepciones = new ArrayList<>();
        if (dto == null)
            throw new GeneroExcepcion(INGRESE_DATOS_REQUERIDOS, HttpStatus.BAD_REQUEST);
        if (dto.getNombreGenero() == null || dto.getNombreGenero().isBlank() || (!dto.getNombreGenero().equals(genero.getNombre())))
            this.hayError = true;
        excepciones.add("El nombre del genero Ingresado es incorrecto");
        if (dto.getImagenGenero() == null || dto.getImagenGenero().isBlank() || (!dto.getImagenGenero().equals(genero.getImagen())))
            this.hayError = true;
        excepciones.add("El nombre de La imagen ingresado es incorrecto");
        if (this.hayError)
            throw new GeneroExcepciones(excepciones, HttpStatus.BAD_REQUEST);
    }

    private void validarEntradaNombreGenero(String nombre) {
        if (nombre != null && !nombre.matches(EXPRECION_NOMBRE)) {
            throw new GeneroExcepcion("El nombre del genero es incorrecto", HttpStatus.BAD_REQUEST);
        }
    }
}
