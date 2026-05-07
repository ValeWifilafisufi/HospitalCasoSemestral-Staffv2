package CasoHospital.Staffv2.service;

import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class StaffService {
    private final StaffRepository staffRepository;

    private StaffResponseDTO mapToDto(Staff staff) {
        return new StaffResponseDTO(
                staff.getNum_registro(),
                staff.getNumrun(),
                staff.getNombre(),
                staff.getP_apellido(),
                staff.getM_apellido(),
                staff.getNombreesp()
        );
    }

    public List<StaffResponseDTO> obtenerTodos() {
        return staffRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<StaffResponseDTO> buscarPorNroRegistro(Long numRegistro) {
        return staffRepository.findById(numRegistro)
                .map(this::mapToDto);
    }

    public List<StaffResponseDTO> buscarPorRun(Long numrun) {
        return staffRepository.findByNumrun(numrun)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public StaffResponseDTO guardar(Staff newstaff){
        Staff staffnuevo = staffRepository.save(newstaff);
        return mapToDto(staffnuevo);
    }

    public Optional<Staff> actualizar(Long nro, Staff staff){
        return staffRepository.findById(nro)
                .map(existente -> {
                    existente.setNombre(staff.getNombre());
                    existente.setP_apellido(staff.getP_apellido());
                    existente.setM_apellido(staff.getM_apellido());
                    existente.setNombreesp(staff.getNombreesp());

                    return staffRepository.save(existente);
                });
    }
}

