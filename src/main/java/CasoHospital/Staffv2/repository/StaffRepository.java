package CasoHospital.Staffv2.repository;

import CasoHospital.Staffv2.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByNumrunIgnoreCase(String numrun);

}
