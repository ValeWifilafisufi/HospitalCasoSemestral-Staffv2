package CasoHospital.Staffv2.service;

import CasoHospital.Staffv2.dtos.StaffRequestDTO;
import CasoHospital.Staffv2.dtos.StaffResponseDTO;
import CasoHospital.Staffv2.exception.RecursoNoEncontradoException;
import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
import CasoHospital.Staffv2.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<StaffResponseDTO> obtenerTodos(Pageable pageable) {
        return staffRepository.findAll(pageable).map(this::mapToDto);
    }

    public StaffResponseDTO buscarPorNroRegistro(Long numRegistro) {
        Staff staff = staffRepository.findById(numRegistro).orElseThrow(() -> new RecursoNoEncontradoException("No existe un staff con numero de registro "+ numRegistro));
        return mapToDto(staff);
    }

    public StaffResponseDTO buscarPorRun(String numrun){
        Staff staff = staffRepository
                .findByNumrunIgnoreCase(numrun)
                .orElseThrow(() ->new RecursoNoEncontradoException("No existe un funcionario con RUN "+ numrun));
        return mapToDto(staff);
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