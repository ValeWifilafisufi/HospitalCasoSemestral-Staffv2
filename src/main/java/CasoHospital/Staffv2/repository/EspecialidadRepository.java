package CasoHospital.Staffv2.repository;

import CasoHospital.Staffv2.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    Page<Especialidad> findByNombreespContainingIgnoreCase(String nombre, Pageable pageable);
}
