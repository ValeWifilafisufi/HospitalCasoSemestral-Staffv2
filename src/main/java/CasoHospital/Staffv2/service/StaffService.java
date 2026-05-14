package CasoHospital.Staffv2.service;

import CasoHospital.Staffv2.dtos.StaffRequestDTO;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
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
    private final EspecialidadRepository especialidadRepository;

    private StaffResponseDTO mapToDto(Staff staff) {
        return new StaffResponseDTO(
                staff.getNum_registro(),
                staff.getNumrun(),
                staff.getNombre(),
                staff.getP_apellido(),
                staff.getM_apellido(),
                staff.getNombreesp().getNombreesp()
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

    public List<StaffResponseDTO> buscarPorRun(String numrun) {
        return staffRepository.findByNumrunContainingIgnoreCase(numrun)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public StaffResponseDTO guardar(StaffRequestDTO dto){

        Especialidad especialidad = especialidadRepository
                .findById(dto.getCod_especialidad())
                .orElseThrow(() ->
                        new RuntimeException("Especialidad no encontrada"));

        Staff nuevoStaff = new Staff();

        nuevoStaff.setNumrun(dto.getNumrun());
        nuevoStaff.setNombre(dto.getNombre());
        nuevoStaff.setP_apellido(dto.getP_apellido());
        nuevoStaff.setM_apellido(dto.getM_apellido());
        nuevoStaff.setNombreesp(especialidad);

        Staff staffGuardado = staffRepository.save(nuevoStaff);

        return mapToDto(staffGuardado);
    }

    public Optional<StaffResponseDTO> actualizar(Long nro, StaffRequestDTO dto) {
        return staffRepository.findById(nro).map(existente -> {
            Especialidad especialidad = especialidadRepository
                    .findById(dto.getCod_especialidad())
                    .orElseThrow(() ->
                            new RuntimeException("Especialidad no encontrada"));
            existente.setNumrun(dto.getNumrun());
            existente.setNombre(dto.getNombre());
            existente.setP_apellido(dto.getP_apellido());
            existente.setM_apellido(dto.getM_apellido());
            existente.setNombreesp(especialidad);
            Staff actualizado = staffRepository.save(existente);
            return mapToDto(actualizado);
        });
    }

    public void eliminar(Long nro){
        staffRepository.deleteById(nro);
    }
}