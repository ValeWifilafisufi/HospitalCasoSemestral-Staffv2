package CasoHospital.Staffv2.config;

import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
import CasoHospital.Staffv2.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final StaffRepository staffRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    public void run(String... args) {

        if (staffRepository.count() > 0) {
            log.info("La BD ya tiene datos");
            return;
        }
        Especialidad esp1 = new Especialidad(123L, "Cardiologia");
        Especialidad esp2 = new Especialidad(234L, "Pediatria");
        Especialidad esp3 = new Especialidad(345L, "Neurologia");
        Especialidad esp4 = new Especialidad(456L, "Traumatologia");
        Especialidad esp5 = new Especialidad(567L, "Dermatologia");
        Especialidad esp6 = new Especialidad(678L, "Ginecologia");
        Especialidad esp7 = new Especialidad(789L, "Oftalmologia");
        Especialidad esp8 = new Especialidad(890L, "Psiquiatria");
        Especialidad esp9 = new Especialidad(901L, "Oncologia");
        Especialidad esp10 = new Especialidad(101L, "Endocrinologia");

        especialidadRepository.save(esp1);
        especialidadRepository.save(esp2);
        especialidadRepository.save(esp3);
        especialidadRepository.save(esp4);
        especialidadRepository.save(esp5);
        especialidadRepository.save(esp6);
        especialidadRepository.save(esp7);
        especialidadRepository.save(esp8);
        especialidadRepository.save(esp9);
        especialidadRepository.save(esp10);

        // STAFF

        staffRepository.save(new Staff(null, "12.345.678-9", "Juan", "Perez", "Gonzalez", esp1));
        staffRepository.save(new Staff(null, "23.456.789-K", "Maria", "Lopez", "Rojas", esp2));
        staffRepository.save(new Staff(null, "34.567.890-1", "Carlos", "Muñoz", "Silva", esp3));
        staffRepository.save(new Staff(null, "45.678.901-2", "Fernanda", "Torres", "Diaz", esp4));
        staffRepository.save(new Staff(null, "56.789.012-3", "Ricardo", "Vargas", "Soto", esp5));
        staffRepository.save(new Staff(null, "67.890.123-4", "Camila", "Herrera", "Castro", esp6));
        staffRepository.save(new Staff(null, "78.901.234-5", "Daniel", "Morales", "Araya", esp7));
        staffRepository.save(new Staff(null, "89.012.345-6", "Valentina", "Fuentes", "Navarro", esp8));
        staffRepository.save(new Staff(null, "90.123.456-7", "Sebastian", "Contreras", "Molina", esp9));
        staffRepository.save(new Staff(null, "11.222.333-8", "Paula", "Reyes", "Espinoza", esp10));

        log.info("Datos de prueba insertados correctamente.");
    }
}
