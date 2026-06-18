package CasoHospital.Staffv2.service;

import CasoHospital.Staffv2.exception.RecursoDuplicadoException;
import CasoHospital.Staffv2.exception.RecursoNoEncontradoException;
import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EspecialidadService {
    private final EspecialidadRepository especialidadRepository;

    public Page<Especialidad> obtenerTodas(Pageable pageable){
        return especialidadRepository.findAll(pageable);
    }

    public Especialidad obtenerPorCodigo(Long cod){
        return especialidadRepository.findById(cod)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe una especialidad con código " + cod));
    }

    @Transactional
    public Especialidad guardar(Especialidad esp){

        if(especialidadRepository.existsById(esp.getCod_especialidad())){
            throw new RecursoDuplicadoException(
                    "La especialidad " +esp.getCod_especialidad() +" ya existe");
        }

        return especialidadRepository.save(esp);
    }

    @Transactional
    public Especialidad actualizar(Long cod,Especialidad esp){
        Especialidad existente =especialidadRepository.findById(cod)
                        .orElseThrow(() ->new RecursoNoEncontradoException(
                                        "No existe una especialidad con código "+ cod));
        existente.setNombreesp(esp.getNombreesp());
        return especialidadRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long cod){
        if(!especialidadRepository.existsById(cod)){
            throw new RecursoNoEncontradoException("No existe una especialidad con código "+ cod);
        }
        especialidadRepository.deleteById(cod);
    }

    public Page<Especialidad> buscarPorNombre(String nombre,Pageable pageable){
        Page<Especialidad> pagina =especialidadRepository.findByNombreespContainingIgnoreCase(nombre,pageable);
        if(pagina.isEmpty()){
            throw new RecursoNoEncontradoException("No existen especialidades con nombre " + nombre);
        }
        return pagina;
    }
}
