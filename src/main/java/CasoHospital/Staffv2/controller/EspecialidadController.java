package CasoHospital.Staffv2.controller;
import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.service.EspecialidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/especialidad")
@RequiredArgsConstructor
@Tag(name = "Gestion de Especialidades", description = "Endpoints para consultar las especialidades medicas")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @Operation(summary = "Obtener todas las especialidades", description = "Retorna una lista paginada de las especialidades del hospital")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Especialidad>>> obtenerTodos(
            @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable,
            @Parameter(hidden = true) PagedResourcesAssembler<Especialidad> pagedAssembler){

        Page<Especialidad> pagina = especialidadService.obtenerTodas(pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(pagina));
    }

    @Operation(summary = "Buscar especialidad por nombre", description = "Retorna especialidades que coincidan con el nombre buscado")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PagedModel<EntityModel<Especialidad>>> obtenerPorNombre(
            @Parameter(description = "Nombre de la especialidad a buscar", example = "Cardiologia")
            @PathVariable String nombre,
            @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable,
            @Parameter(hidden = true) PagedResourcesAssembler<Especialidad> pagedAssembler){

        Page<Especialidad> pagina = especialidadService.buscarPorNombre(nombre, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(pagina));
    }
}