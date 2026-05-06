package CasoHospital.Staffv2.service;

import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class StaffService {

    private final StaffRepository staffRepository;

    private StaffResponseDTO mapToDtO(Staff staff){
        return new StaffResponseDTO(
                staff.getNum_registro(),
                staff.getNumrun(),
                staff.getNombre(),
                staff.getP_apellido(),
                staff.getM_apellido(),
                staff.getNombreesp()
        );
    }
    public List<StaffResponseDTO> obtenerTodos(){
        return staffRepository.findAll().stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }
}
