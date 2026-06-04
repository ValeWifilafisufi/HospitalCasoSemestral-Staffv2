package CasoHospital.Staffv2.assemblers;

import CasoHospital.Staffv2.controller.StaffControllerV2;
import CasoHospital.Staffv2.model.Staff;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffModelAssembler {

    @Override
    public EntityModel<Staff> toModel(Staff Staff) {
        return EntityModel.of(Staff,
                linkTo(methodOn(StaffControllerV2.class).(carrera.getCodigo())).withSelfRel(),
                linkTo(methodOn(StaffControllerV2.class).getAllCarreras()).withRel("carreras"));
    }
    }

