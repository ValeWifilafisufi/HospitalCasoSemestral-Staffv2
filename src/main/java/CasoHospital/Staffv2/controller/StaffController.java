package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.dtos.StaffRequestDTO;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor

public class StaffController {
    private final StaffService staffService;

    @GetMapping
    public ResponseEntity<List<StaffResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(staffService.obtenerTodos());
    }

    @GetMapping("/nro_registro/{nro_re}")
    public ResponseEntity<StaffResponseDTO> obtenerPorNroRe(@PathVariable Long nro_re){
        return staffService.buscarPorNroRegistro(nro_re).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<List<StaffResponseDTO>> obtenerPorRun(@PathVariable Long run){
        return ResponseEntity.ok(staffService.buscarPorRun(run));
    }

    @PostMapping
    public ResponseEntity<StaffResponseDTO> guardar(@Valid @RequestBody StaffRequestDTO staff){
        return ResponseEntity.status(201).body(staffService.guardar(staff));
    }
}
