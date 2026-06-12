package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.service.EspecialidadService;
import CasoHospital.Staffv2.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/especialidad")
@RequiredArgsConstructor

public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<Page<Especialidad>> obtenerTodos(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(especialidadService.obtenerTodas(pageable));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Page<Especialidad>> obtenerPorNombre(
            @PathVariable String nombre,
            @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(especialidadService.buscarPorNombre(nombre, pageable));
    }
}