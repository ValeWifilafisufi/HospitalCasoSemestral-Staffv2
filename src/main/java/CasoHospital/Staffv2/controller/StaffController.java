package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.assemblers.StaffModelAssembler;
import CasoHospital.Staffv2.dtos.StaffRequestDTO;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Tag(name = "Gestion de staff V1", description = "Endpoints para administrar el sistema de Staff del Hospital")
public class StaffController {

    private final StaffService staffService;
    private final StaffModelAssembler assembler;

    //-----------------OBTENER TODO EL STAFF ----------
    @Operation(summary = "Obtener todo el staff", description = "Retorna una lista con todo el staff registrado")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<StaffResponseDTO>>> obtenerTodos(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            PagedResourcesAssembler<StaffResponseDTO> pagedAssembler) {

        Page<StaffResponseDTO> paginaStaff = staffService.obtenerTodos(pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(paginaStaff, assembler));
    }

    //-----------------BUSCAR POR NRO REGISTRO----------
    @Operation(summary = "Obtener el staff por numero de registro", description = "Retorna el staff que contenga el numero de registro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El numero de registro no coincide con ningun staff")
    })
    @GetMapping("/nro_registro/{nro_re}")
    public ResponseEntity<EntityModel<StaffResponseDTO>> obtenerPorNroRe(
            @Parameter(description = "Numero de registro para buscar", example = "1") @PathVariable Long nro_re) {

        return staffService.buscarPorNroRegistro(nro_re)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------BUSCAR POR RUT DEL STAFF----------
    @Operation(summary = "Obtener el staff por rut", description = "Retorna el staff que contenga ese rut")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El rut ingresado no coincide con ningun staff")
    })
    @GetMapping("/run/{run}")
    public ResponseEntity<EntityModel<StaffResponseDTO>> obtenerPorRun(
            @Parameter(description = "Rut para buscar", example = "11.111.111-1") @PathVariable String run) {

        return staffService.buscarPorRun(run)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------GUARDAR STAFF----------
    @Operation(summary = "Creacion de staff", description = "Se guarda el Staff con un nro de registro automatico junto a los datos entregados")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Staff creado exitosamente")
    })
    @PostMapping
    public ResponseEntity<EntityModel<StaffResponseDTO>> guardar(
            @Valid @RequestBody StaffRequestDTO dto) {

        StaffResponseDTO staffGuardado = staffService.guardar(dto);
        return ResponseEntity
                .status(201)
                .body(assembler.toModel(staffGuardado));
    }

    //-----------------ACTUALIZAR STAFF----------
    @Operation(summary = "Actualizacion del staff", description = "Se actualiza el staff mediante el numero de registro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "El numero de registro ingresado no coincide con ningun staff")
    })
    @PutMapping("/{nro}")
    public ResponseEntity<EntityModel<StaffResponseDTO>> actualizar(
            @Parameter(description = "Numero de registro para buscar", example = "1") @PathVariable Long nro,
            @Valid @RequestBody StaffRequestDTO dto) {

        return staffService.actualizar(nro, dto)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------ELIMINAR STAFF----------
    @Operation(summary = "Eliminacion del staff", description = "Se elimina el staff mediante el numero de registro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Staff eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El numero de registro ingresado no coincide con ningun staff")
    })
    @DeleteMapping("/{nro}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Numero de registro para buscar", example = "1") @PathVariable Long nro) {

        if (staffService.buscarPorNroRegistro(nro).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        staffService.eliminar(nro);
        return ResponseEntity.noContent().build();
    }
}