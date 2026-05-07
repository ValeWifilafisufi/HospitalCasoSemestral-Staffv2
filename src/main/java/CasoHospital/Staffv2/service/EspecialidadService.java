package CasoHospital.Staffv2.service;

import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class EspecialidadService {
    private final EspecialidadRepository especialidadRepository;

    public List<Especialidad> obtenerTodas(){
        return especialidadRepository.findAll();
    }

    public Optional<Especialidad> obtenerPorCodigo(Long cod){
        return especialidadRepository.findById(cod);
    }

    public Especialidad guardar (Especialidad esp){
        return especialidadRepository.save(esp);
    }

    public Optional<Especialidad> actualizar(Long cod, Especialidad esp){
        return especialidadRepository.findById(cod)
                .map(existente -> {
                    existente.setNombreesp(esp.getNombreesp());
                    return especialidadRepository.save(existente);
                });
    }

    public void eliminar(Long cod){
        especialidadRepository.deleteById(cod);
    }

    public List<Especialidad> buscarPorNombre(String nombre){
        return especialidadRepository.findByNombreContainingIgnoreCase(nombre);
    }


}
