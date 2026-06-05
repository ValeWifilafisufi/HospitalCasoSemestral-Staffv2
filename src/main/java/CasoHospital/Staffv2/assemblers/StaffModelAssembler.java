package CasoHospital.Staffv2.assemblers;

import CasoHospital.Staffv2.controller.StaffController;
import CasoHospital.Staffv2.controller.StaffControllerV2;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.model.Staff;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffModelAssembler implements RepresentationModelAssembler<StaffResponseDTO, EntityModel<StaffResponseDTO>> {

    @Override
    public EntityModel<StaffResponseDTO> toModel(StaffResponseDTO Staff) {
        return EntityModel.of(Staff,
                linkTo(methodOn(StaffControllerV2.class)
                        .obtenerPorNroRe(staff.getNum_registro()))
                        .withSelfRel(),
                linkTo(methodOn(StaffControllerV2.class)
                        .obtenerTodos())
                        .withRel("todos")

    }

