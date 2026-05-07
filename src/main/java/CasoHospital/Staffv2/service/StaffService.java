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
                .map(this::mapToDto).collect(Collectors.toList());
    }

    public Optional<Staff> buscarPorNroRegistro(Long numRegistro) {
        return staffRepository.findById(numRegistro);
    }

    public List<Staff> buscarPorRun(Long numrun) {
        return staffRepository.findByNumrunContainingIgnoreCase(numrun);
    }

    public List<Staff> buscarPorEspecialidad(String esp) {
        return staffRepository.findByEspecialidad(esp);
    }

    public Staff guardar(Staff newstaff){
        return staffRepository.save(newstaff);
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
