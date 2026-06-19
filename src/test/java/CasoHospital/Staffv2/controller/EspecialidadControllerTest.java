package CasoHospital.Staffv2.controller;
import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.service.EspecialidadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import CasoHospital.Staffv2.security.JwtService;

@WebMvcTest(EspecialidadController.class)
@ActiveProfiles("test")
@WithMockUser
@DisplayName("Tests Unitarios - EspecialidadController")
public class EspecialidadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EspecialidadService especialidadService;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("GIVEN: Existen especialidades WHEN: GET /api/especialidad THEN: Retorna 200 OK y la lista paginada")
    void shouldReturnTodasLasEspecialidades() throws Exception {
        Especialidad esp1 = new Especialidad();
        esp1.setCod_especialidad(1L);
        esp1.setNombreesp("Cardiología");

        Especialidad esp2 = new Especialidad();
        esp2.setCod_especialidad(2L);
        esp2.setNombreesp("Pediatría");

        List<Especialidad> listaFalsa = Arrays.asList(esp1, esp2);
        Page<Especialidad> paginaFalsa = new PageImpl<>(listaFalsa);

        when(especialidadService.obtenerTodas(any(Pageable.class))).thenReturn(paginaFalsa);

        mockMvc.perform(get("/api/especialidad")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.especialidadList[0].nombreesp").value("Cardiología"))
                .andExpect(jsonPath("$._embedded.especialidadList[1].nombreesp").value("Pediatría"));
    }

    @Test
    @DisplayName("GIVEN: Nombre válido WHEN: GET /api/especialidad/nombre/{nombre} THEN: Retorna 200 OK")
    void shouldReturnEspecialidadPorNombre() throws Exception {
        Especialidad esp = new Especialidad();
        esp.setCod_especialidad(1L);
        esp.setNombreesp("Neurología");

        Page<Especialidad> paginaFalsa = new PageImpl<>(List.of(esp));

        when(especialidadService.buscarPorNombre(anyString(), any(Pageable.class))).thenReturn(paginaFalsa);

        mockMvc.perform(get("/api/especialidad/nombre/Neurología")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.especialidadList[0].nombreesp").value("Neurología"));
    }
}