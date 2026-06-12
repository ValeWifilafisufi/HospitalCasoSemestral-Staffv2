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

    public Optional<StaffResponseDTO> buscarPorNroRegistro(Long numRegistro) {
        return staffRepository.findById(numRegistro).map(this::mapToDto);
    }

    public Optional<StaffResponseDTO> buscarPorRun(String numrun){
        return staffRepository.findByNumrunIgnoreCase(numrun).map(this::mapToDto);
    }

    @Transactional
    public StaffResponseDTO guardar(StaffRequestDTO dto){
        Especialidad especialidad = especialidadRepository
                .findById(dto.getCod_especialidad())
                .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada con código: " + dto.getCod_especialidad()));

        Staff nuevoStaff = new Staff();
        nuevoStaff.setNumrun(dto.getNumrun());
        nuevoStaff.setNombre(dto.getNombre());
        nuevoStaff.setP_apellido(dto.getP_apellido());
        nuevoStaff.setM_apellido(dto.getM_apellido());
        nuevoStaff.setNombreesp(especialidad);

        Staff staffGuardado = staffRepository.save(nuevoStaff);
        return mapToDto(staffGuardado);
    }

    @Transactional
    public Optional<StaffResponseDTO> actualizar(Long nro, StaffRequestDTO dto) {
        return staffRepository.findById(nro).map(existente -> {
            Especialidad especialidad = especialidadRepository
                    .findById(dto.getCod_especialidad())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada con código: " + dto.getCod_especialidad()));
            existente.setNombre(dto.getNombre());
            existente.setP_apellido(dto.getP_apellido());
            existente.setM_apellido(dto.getM_apellido());
            existente.setNombreesp(especialidad);
            Staff actualizado = staffRepository.save(existente);
            return mapToDto(actualizado);
        });
    }

    @Transactional
    public void eliminar(Long nro){
        if(!staffRepository.existsById(nro)){
            throw new RecursoNoEncontradoException("No existe un staff con numero de registro " + nro);
        }
        staffRepository.deleteById(nro);
    }
}