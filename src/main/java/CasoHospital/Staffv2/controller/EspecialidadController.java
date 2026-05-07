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
    public ResponseEntity<List<Especialidad>> obtenerTodos(){
        return ResponseEntity.ok(especialidadService.obtenerTodas();
    }

    @GetMapping("/id{id}")
    public ResponseEntity<List<Especialidad>> obtenerPorNombre(@PathVariable String nombre){
        return ResponseEntity.ok(especialidadService.buscarPorNombre(nombre));
    }

}
