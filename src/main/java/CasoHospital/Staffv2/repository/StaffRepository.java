package CasoHospital.Staffv2.repository;

import CasoHospital.Staffv2.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findByNumrunContainingIgnoreCase(Long numrun);
    List<Staff> findByEspecialidad(String especialidad);

}
