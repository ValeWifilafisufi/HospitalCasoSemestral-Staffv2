package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
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
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Especialidad>>> obtenerTodos(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            PagedResourcesAssembler<Especialidad> pagedAssembler){

        Page<Especialidad> pagina = especialidadService.obtenerTodas(pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(pagina));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PagedModel<EntityModel<Especialidad>>> obtenerPorNombre(
            @PathVariable String nombre,
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            PagedResourcesAssembler<Especialidad> pagedAssembler){

        Page<Especialidad> pagina = especialidadService.buscarPorNombre(nombre, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(pagina));
    }
}