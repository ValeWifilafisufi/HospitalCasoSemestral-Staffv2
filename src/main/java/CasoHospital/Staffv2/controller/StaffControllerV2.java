package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.assemblers.StaffModelAssembler;
import CasoHospital.Staffv2.dtos.StaffRequestDTO;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.service.StaffService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/staff")
@RequiredArgsConstructor
@Tag(name = "Gestion de staff V1", description = "Endpoints para administrar el sistema de Previsiones de el Hospital")

public class StaffControllerV2 {

    private final StaffService staffService;
    private final StaffModelAssembler assembler;

    //-----------------OBTENER TODO EL STAFF----------
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<StaffResponseDTO>>> obtenerTodos() {
        List<EntityModel<StaffResponseDTO>> staff = staffService.obtenerTodos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(
                        staff,
                        linkTo(methodOn(StaffControllerV2.class)
                                .obtenerTodos())
                                .withSelfRel()
                )
        );
    }

    //-----------------BUSCAR POR NRO REGISTRO----------
    @GetMapping("/nro_registro/{nro_re}")
    public ResponseEntity<EntityModel<StaffResponseDTO>> obtenerPorNroRe(
            @PathVariable Long nro_re) {
        return staffService.buscarPorNroRegistro(nro_re)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------BUSCAR POR RUN----------
    @GetMapping("/run/{run}")
    public ResponseEntity<CollectionModel<EntityModel<StaffResponseDTO>>> obtenerPorRun(
            @PathVariable String run) {
        List<EntityModel<StaffResponseDTO>> staff = staffService.buscarPorRun(run)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(
                        staff,
                        linkTo(methodOn(StaffControllerV2.class)
                                .obtenerPorRun(run))
                                .withSelfRel()
                )
        );
    }

    //-----------------GUARDAR----------
    @PostMapping
    public ResponseEntity<EntityModel<StaffResponseDTO>> guardar(
            @Valid @RequestBody StaffRequestDTO dto) {
        StaffResponseDTO staffGuardado = staffService.guardar(dto);
        return ResponseEntity
                .status(201)
                .body(assembler.toModel(staffGuardado));
    }

    //-----------------ACTUALIZAR----------
    @PutMapping("/{nro}")
    public ResponseEntity<EntityModel<StaffResponseDTO>> actualizar(
            @PathVariable Long nro,
            @Valid @RequestBody StaffRequestDTO dto) {
        return staffService.actualizar(nro, dto)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------ELIMINAR----------
    @DeleteMapping("/{nro}")
    public ResponseEntity<Void> eliminar(@PathVariable Long nro) {
        if (staffService.buscarPorNroRegistro(nro).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        staffService.eliminar(nro);
        return ResponseEntity.noContent().build();
    }
}