package CasoHospital.Staffv2.repository;

import CasoHospital.Staffv2.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    List<Especialidad> findByNombreContainingIgnoreCase(String nombre);

}
