package CasoHospital.Staffv2.assemblers;

import CasoHospital.Staffv2.controller.StaffController;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class StaffModelAssembler implements RepresentationModelAssembler<StaffResponseDTO, EntityModel<StaffResponseDTO>> {

    @Override
    public EntityModel<StaffResponseDTO> toModel(StaffResponseDTO staff) {
        return EntityModel.of(staff,
                linkTo(methodOn(StaffController.class)
                        .obtenerPorNroRe(staff.getNum_registro()))
                        .withSelfRel(),
                linkTo(methodOn(StaffController.class)
                        .obtenerTodos(null,null))
                        .withRel("Todos-los-staff")
        );
    }
}