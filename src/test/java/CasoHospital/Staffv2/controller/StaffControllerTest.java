package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.assemblers.StaffModelAssembler;
import CasoHospital.Staffv2.dtos.StaffRequestDTO;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.security.JwtService;
import CasoHospital.Staffv2.service.StaffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StaffController.class)
@ActiveProfiles("test")
@Import(StaffModelAssembler.class)
@WithMockUser
@DisplayName("Tests Unitarios - StaffController")
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StaffService staffService;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("GIVEN: Existen médicos WHEN: GET /api/staff THEN: Retorna 200 OK y la lista paginada")
    void shouldReturnTodosLosStaff() throws Exception {
        StaffResponseDTO doc1 = new StaffResponseDTO();
        doc1.setNum_registro(1L);
        doc1.setNombre("Claudio");
        doc1.setNombreesp("Cardiología");

        StaffResponseDTO doc2 = new StaffResponseDTO();
        doc2.setNum_registro(2L);
        doc2.setNombre("Micaela");
        doc2.setNombreesp("Pediatría");

        List<StaffResponseDTO> listaFalsa = Arrays.asList(doc1, doc2);
        Page<StaffResponseDTO> paginaFalsa = new PageImpl<>(listaFalsa);

        when(staffService.obtenerTodos(any(Pageable.class))).thenReturn(paginaFalsa);

        mockMvc.perform(get("/api/staff")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.staffResponseDTOList[0].nombre").value("Claudio"))
                .andExpect(jsonPath("$._embedded.staffResponseDTOList[1].nombre").value("Micaela"));
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: GET /api/staff/nro_registro/{id} THEN: Retorna 200 OK")
    void shouldReturnStaffById() throws Exception {
        StaffResponseDTO doc = new StaffResponseDTO();
        doc.setNum_registro(1L);
        doc.setNombre("Claudio");

        when(staffService.buscarPorNroRegistro(anyLong())).thenReturn(Optional.of(doc));

        mockMvc.perform(get("/api/staff/nro_registro/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Claudio"));
    }

    @Test
    @DisplayName("GIVEN: DTO válido WHEN: POST /api/staff THEN: Retorna 201 Created")
    void shouldCreateStaff() throws Exception {
        StaffRequestDTO request = new StaffRequestDTO();
        request.setNumrun("19.888.777-6");
        request.setNombre("Juan");
        request.setP_apellido("Perez");
        request.setM_apellido("Gonzalez");
        request.setCod_especialidad(1L);

        StaffResponseDTO response = new StaffResponseDTO();
        response.setNum_registro(31L);
        response.setNombre("Juan");
        response.setNombreesp("Cardiología");

        when(staffService.guardar(any(StaffRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/staff")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.num_registro").value(31))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    @DisplayName("GIVEN: ID y DTO válidos WHEN: PUT /api/staff/{id} THEN: Retorna 200 OK")
    void shouldUpdateStaff() throws Exception {
        Long idStaff = 1L;

        StaffRequestDTO updateRequest = new StaffRequestDTO();
        updateRequest.setNumrun("19.888.777-6");
        updateRequest.setNombre("Juanito");
        updateRequest.setP_apellido("Perez");
        updateRequest.setM_apellido("Gonzalez");
        updateRequest.setCod_especialidad(2L);

        StaffResponseDTO response = new StaffResponseDTO();
        response.setNum_registro(1L);
        response.setNombre("Juanito");
        response.setNombreesp("Pediatría");

        when(staffService.actualizar(eq(idStaff), any(StaffRequestDTO.class))).thenReturn(Optional.of(response));

        mockMvc.perform(put("/api/staff/" + idStaff)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juanito"))
                .andExpect(jsonPath("$.nombreesp").value("Pediatría"));
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: DELETE /api/staff/{id} THEN: Retorna 204 No Content")
    void shouldDeleteStaff() throws Exception {
        Long idStaff = 1L;
        when(staffService.buscarPorNroRegistro(idStaff)).thenReturn(Optional.of(new StaffResponseDTO()));
        doNothing().when(staffService).eliminar(idStaff);

        mockMvc.perform(delete("/api/staff/" + idStaff).with(csrf()))
                .andExpect(status().isNoContent());
    }
}