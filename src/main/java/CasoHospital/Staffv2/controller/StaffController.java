package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.dtos.StaffRequestDTO;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Tag(name = "Gestion de staff V1", description = "Endpoints para administrar el sistema de Previsiones de el Hospital")
public class StaffController {

    @Autowired
    private final StaffService staffService;

    //-----------------BUSCAR A TODO EL STAFF-----------
    @Operation(summary = "Obtener todo el staff", description = "Retorna una lista con todo el staff registrado")
    @GetMapping
    public ResponseEntity<List<StaffResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(staffService.obtenerTodos());
    }

    //-----------------BUSCAR POR NUMERO DE REGISTRO----------
    @Operation(summary = "Obtener el staff por numero de registro", description = "Retorna el staff que contenga el numero de registro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El numero de registro no coincide con ningun staff")
    })
    @GetMapping("/nro_registro/{nro_re}")
    public ResponseEntity<StaffResponseDTO> obtenerPorNroRe(@Parameter(description = "Numero de registro para buscar", example = "1")
                                                                @PathVariable Long nro_re){
        return staffService.buscarPorNroRegistro(nro_re)
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
    public ResponseEntity<List<StaffResponseDTO>> obtenerPorRun(@Parameter(description = "Rut para buscar", example = "11.111.111-1")
            @PathVariable String run){
        return ResponseEntity.ok(staffService.buscarPorRun(run));
    }

    //-----------------GUARDAR STAFF----------
    @Operation(summary = "Creacion de staff", description = "Se guarda el Staff con un nro de registro automatico y autoincrementable junto a los datos entregados ")
     @ApiResponses({
             @ApiResponse(responseCode = "201", description = "Staff creado exitosamente")
     })
    @PostMapping
    public ResponseEntity<StaffResponseDTO> guardar(
            @Valid @RequestBody StaffRequestDTO staff){
        return ResponseEntity.status(201).body(staffService.guardar(staff));
    }


    //-----------------ACTUALIZAR STAFF----------
    @Operation(summary = "Actualizacion del staff", description = "Se actualiza el staff mediante el numero de registro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "El numero de registro ingresado no coincide con ningun staff")
    })
    @PutMapping("/{nro}")
    public ResponseEntity<StaffResponseDTO> actualizar(@Parameter(description = "numero de registro para busc ar al staff", example = "1") @PathVariable Long nro, @Valid @RequestBody StaffRequestDTO dto){
        return staffService.actualizar(nro, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------ELIMINAR STAFF----------
    @Operation(summary = "Elimicacion del staff", description = "Se elimina el staff mediante el numero de registro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El numero de registro ingresado no coincide con ningun staff")
    })
    @DeleteMapping("/{nro}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Numero de registro para buscar al staff", example = "1") @PathVariable Long nro){
        if (staffService.buscarPorNroRegistro(nro).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        staffService.eliminar(nro);
        return ResponseEntity.noContent().build();
    }
}
