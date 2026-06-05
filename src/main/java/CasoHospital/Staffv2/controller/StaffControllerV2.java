package CasoHospital.Staffv2.controller;

import CasoHospital.Staffv2.assemblers.StaffModelAssembler;
import CasoHospital.Staffv2.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/staff")
@RequiredArgsConstructor


public class StaffControllerV2 {

    private final StaffService staffService;
    private final StaffModelAssembler assembler;
}
