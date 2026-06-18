package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.exception.RecursoDuplicadoException;
import CasoHospital.Staffv2.exception.RecursoNoEncontradoException;
import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
import CasoHospital.Staffv2.service.EspecialidadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - EspecialidadService")
public class EspecialidadServiceTest {

    @Mock
    private EspecialidadRepository especialidadRepository;

    @InjectMocks
    private EspecialidadService especialidadService;

    @Test
    @DisplayName("GIVEN: Pageable WHEN: obtenerTodas THEN: Retorna Page de Especialidades")
    void shouldObtenerTodas() {
        Especialidad esp1 = new Especialidad();
        esp1.setCod_especialidad(1L);
        esp1.setNombreesp("Cardiología");

        Page<Especialidad> paginaFalsa = new PageImpl<>(List.of(esp1));
        Pageable pageable = PageRequest.of(0, 10);

        when(especialidadRepository.findAll(pageable)).thenReturn(paginaFalsa);

        Page<Especialidad> resultado = especialidadService.obtenerTodas(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(especialidadRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: obtenerPorCodigo THEN: Retorna la Especialidad")
    void shouldObtenerPorCodigo() {
        Especialidad esp = new Especialidad();
        esp.setCod_especialidad(1L);
        esp.setNombreesp("Cardiología");

        when(especialidadRepository.findById(1L)).thenReturn(Optional.of(esp));

        Especialidad resultado = especialidadService.obtenerPorCodigo(1L);

        assertNotNull(resultado);
        assertEquals("Cardiología", resultado.getNombreesp());
        verify(especialidadRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: obtenerPorCodigo THEN: Lanza Exception")
    void shouldThrowExceptionWhenObtenerPorCodigoNoExiste() {
        when(especialidadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            especialidadService.obtenerPorCodigo(99L);
        });
    }

    @Test
    @DisplayName("GIVEN: Especialidad nueva WHEN: guardar THEN: Retorna Especialidad guardada")
    void shouldGuardarEspecialidad() {
        Especialidad esp = new Especialidad();
        esp.setCod_especialidad(10L);
        esp.setNombreesp("Dermatología");

        when(especialidadRepository.existsById(10L)).thenReturn(false);
        when(especialidadRepository.save(esp)).thenReturn(esp);

        Especialidad resultado = especialidadService.guardar(esp);

        assertNotNull(resultado);
        assertEquals("Dermatología", resultado.getNombreesp());
        verify(especialidadRepository, times(1)).save(esp);
    }

    @Test
    @DisplayName("GIVEN: Especialidad existente WHEN: guardar THEN: Lanza Exception")
    void shouldThrowExceptionWhenGuardarDuplicado() {
        Especialidad esp = new Especialidad();
        esp.setCod_especialidad(10L);

        when(especialidadRepository.existsById(10L)).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> {
            especialidadService.guardar(esp);
        });

        verify(especialidadRepository, never()).save(any(Especialidad.class));
    }

    @Test
    @DisplayName("GIVEN: ID y Datos válidos WHEN: actualizar THEN: Retorna Especialidad actualizada")
    void shouldActualizarEspecialidad() {
        Especialidad existente = new Especialidad();
        existente.setCod_especialidad(1L);
        existente.setNombreesp("Medicina General");

        Especialidad actualizacion = new Especialidad();
        actualizacion.setNombreesp("Medicina Interna");

        when(especialidadRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(especialidadRepository.save(any(Especialidad.class))).thenReturn(existente);

        Especialidad resultado = especialidadService.actualizar(1L, actualizacion);

        assertNotNull(resultado);
        assertEquals("Medicina Interna", resultado.getNombreesp()); // Verificamos que se actualizó el nombre
        verify(especialidadRepository, times(1)).save(existente);
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: eliminar THEN: Ejecuta deleteById")
    void shouldEliminarEspecialidad() {
        Long id = 1L;
        when(especialidadRepository.existsById(id)).thenReturn(true);
        doNothing().when(especialidadRepository).deleteById(id);

        especialidadService.eliminar(id);

        verify(especialidadRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: eliminar THEN: Lanza Exception")
    void shouldThrowExceptionWhenEliminarNoExiste() {
        Long id = 99L;
        when(especialidadRepository.existsById(id)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> {
            especialidadService.eliminar(id);
        });

        verify(especialidadRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("GIVEN: Nombre existente WHEN: buscarPorNombre THEN: Retorna Page")
    void shouldBuscarPorNombre() {
        Especialidad esp = new Especialidad();
        esp.setNombreesp("Neurología");

        Page<Especialidad> paginaResultados = new PageImpl<>(List.of(esp));
        Pageable pageable = PageRequest.of(0, 10);

        when(especialidadRepository.findByNombreespContainingIgnoreCase("Neuro", pageable))
                .thenReturn(paginaResultados);

        Page<Especialidad> resultado = especialidadService.buscarPorNombre("Neuro", pageable);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getContent().size());
    }

    @Test
    @DisplayName("GIVEN: Nombre inexistente WHEN: buscarPorNombre THEN: Lanza Exception")
    void shouldThrowExceptionWhenBuscarPorNombreNoEncuentraNada() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Especialidad> paginaVacia = new PageImpl<>(Collections.emptyList());

        when(especialidadRepository.findByNombreespContainingIgnoreCase("Inexistente", pageable))
                .thenReturn(paginaVacia);

        assertThrows(RecursoNoEncontradoException.class, () -> {
            especialidadService.buscarPorNombre("Inexistente", pageable);
        });
    }
}