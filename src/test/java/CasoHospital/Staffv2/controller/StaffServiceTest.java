package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.dtos.StaffRequestDTO;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.exception.RecursoNoEncontradoException;
import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
import CasoHospital.Staffv2.repository.StaffRepository;
import CasoHospital.Staffv2.service.StaffService;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Usamos Mockito puro sin levantar Spring
@DisplayName("Tests Unitarios - StaffService")
public class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private EspecialidadRepository especialidadRepository;

    @InjectMocks
    private StaffService staffService; // Inyecta los mocks de arriba en el servicio

    @Test
    @DisplayName("GIVEN: Pageable WHEN: obtenerTodos THEN: Retorna Page de StaffResponseDTO")
    void shouldObtenerTodos() {
        // 1. Preparar datos
        Especialidad especialidad = new Especialidad();
        especialidad.setNombreesp("Cardiología");

        Staff doc1 = new Staff();
        doc1.setNum_registro(1L);
        doc1.setNombre("Claudio");
        doc1.setNombreesp(especialidad);

        Page<Staff> paginaEntidad = new PageImpl<>(List.of(doc1));
        Pageable pageable = PageRequest.of(0, 10);

        // 2. Comportamiento del Mock
        when(staffRepository.findAll(pageable)).thenReturn(paginaEntidad);

        // 3. Ejecutar
        Page<StaffResponseDTO> resultado = staffService.obtenerTodos(pageable);

        // 4. Verificar
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        assertEquals("Claudio", resultado.getContent().get(0).getNombre());
        assertEquals("Cardiología", resultado.getContent().get(0).getNombreesp());
        verify(staffRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: buscarPorNroRegistro THEN: Retorna Optional con DTO")
    void shouldBuscarPorNroRegistro() {
        Especialidad esp = new Especialidad();
        esp.setNombreesp("Pediatría");

        Staff doc = new Staff();
        doc.setNum_registro(10L);
        doc.setNombre("Micaela");
        doc.setNombreesp(esp);

        when(staffRepository.findById(10L)).thenReturn(Optional.of(doc));

        Optional<StaffResponseDTO> resultado = staffService.buscarPorNroRegistro(10L);

        assertTrue(resultado.isPresent());
        assertEquals("Micaela", resultado.get().getNombre());
        verify(staffRepository, times(1)).findById(10L);
    }

    @Test
    @DisplayName("GIVEN: DTO válido WHEN: guardar THEN: Retorna DTO guardado")
    void shouldGuardarStaffExitosamente() {
        StaffRequestDTO request = new StaffRequestDTO();
        request.setNumrun("19.123.456-7");
        request.setNombre("Juan");
        request.setCod_especialidad(1L);

        Especialidad esp = new Especialidad();
        esp.setCod_especialidad(1L);
        esp.setNombreesp("Neurología");

        Staff docGuardado = new Staff();
        docGuardado.setNum_registro(100L);
        docGuardado.setNumrun("19.123.456-7");
        docGuardado.setNombre("Juan");
        docGuardado.setNombreesp(esp);

        // Simulamos que encuentra la especialidad
        when(especialidadRepository.findById(1L)).thenReturn(Optional.of(esp));
        // Simulamos el guardado
        when(staffRepository.save(any(Staff.class))).thenReturn(docGuardado);

        StaffResponseDTO resultado = staffService.guardar(request);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getNum_registro());
        assertEquals("Neurología", resultado.getNombreesp());
        verify(staffRepository, times(1)).save(any(Staff.class));
    }

    @Test
    @DisplayName("GIVEN: Especialidad inexistente WHEN: guardar THEN: Lanza Exception")
    void shouldThrowExceptionWhenEspecialidadNoExiste() {
        StaffRequestDTO request = new StaffRequestDTO();
        request.setCod_especialidad(99L); // ID que no existe

        // Simulamos que NO encuentra la especialidad
        when(especialidadRepository.findById(99L)).thenReturn(Optional.empty());

        // Verificamos que al ejecutar guardar, salta la excepción esperada
        RecursoNoEncontradoException exception = assertThrows(RecursoNoEncontradoException.class, () -> {
            staffService.guardar(request);
        });

        assertTrue(exception.getMessage().contains("Especialidad no encontrada"));
        verify(staffRepository, never()).save(any(Staff.class)); // Aseguramos que nunca intente guardar
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: eliminar THEN: Ejecuta deleteById")
    void shouldEliminarStaff() {
        Long id = 1L;
        when(staffRepository.existsById(id)).thenReturn(true);
        doNothing().when(staffRepository).deleteById(id);

        staffService.eliminar(id);

        verify(staffRepository, times(1)).existsById(id);
        verify(staffRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: eliminar THEN: Lanza Exception")
    void shouldThrowExceptionWhenEliminarNoExiste() {
        Long id = 99L;
        when(staffRepository.existsById(id)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> {
            staffService.eliminar(id);
        });

        verify(staffRepository, never()).deleteById(anyLong()); // Nunca debe llegar a borrar
    }
}