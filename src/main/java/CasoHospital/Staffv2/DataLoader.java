package CasoHospital.Staffv2;

import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.model.Role;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.model.User;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
import CasoHospital.Staffv2.repository.StaffRepository;
import CasoHospital.Staffv2.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final StaffRepository staffRepository;
    private final EspecialidadRepository especialidadRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("valentina").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("valentina");
            adminUser.setPassword(passwordEncoder.encode("123")); // Usa la misma clave de Habitaciones
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);
            log.info("Usuario administrador creado automáticamente (username: valentina).");
        } else {
            log.info("El usuario administrador (valentina) ya existe en la base de datos.");
        }

        if (userRepository.findByUsername("Maty").isEmpty()) {
            User adminUser2 = new User();
            adminUser2.setUsername("Maty");
            adminUser2.setPassword(passwordEncoder.encode("1234"));
            adminUser2.setRole(Role.ADMIN);
            userRepository.save(adminUser2);
            log.info("Usuario administrador creado automáticamente (username: Maty).");
        } else {
            log.info("El usuario administrador (Maty) ya existe en la base de datos.");
        }

        // 2. CREACIÓN DE DATOS (STAFF Y ESPECIALIDADES)
        if (staffRepository.count() > 0 || especialidadRepository.count() > 0) {
            log.info("La BD de Staff/Especialidad ya tiene datos. Saltando carga inicial de Faker.");
            return;
        }

        log.info("Iniciando carga masiva de datos médicos...");
        Faker faker = new Faker(Locale.forLanguageTag("es"));
        List<Especialidad> especialidadesGuardadas = new ArrayList<>();

        // LISTA ACTUALIZADA CON LAS 12 ESPECIALIDADES MÁS COMUNES
        String[] nombresReales = {
                "Cardiología", "Pediatría", "Dermatología", "Traumatología y Ortopedia",
                "Ginecología y Obstetricia", "Neurología", "Oftalmología", "Psiquiatría",
                "Gastroenterología", "Cirugía General", "Medicina Interna", "Otorrinolaringología"
        };

        for (int i = 0; i < nombresReales.length; i++) {
            Especialidad esp = new Especialidad();
            esp.setCod_especialidad((long) (i + 1));
            esp.setNombreesp(nombresReales[i]);
            especialidadesGuardadas.add(especialidadRepository.save(esp));
        }

        for (int i = 0; i < 100; i++) {
            Staff staff = new Staff();
            String runFake = faker.number().numberBetween(10, 99) + "." +
                    faker.number().numberBetween(100, 999) + "." +
                    faker.number().numberBetween(100, 999) + "-" +
                    faker.number().numberBetween(0, 9);
            staff.setNumrun(runFake);
            staff.setNombre(faker.name().firstName());
            staff.setP_apellido(faker.name().lastName());
            staff.setM_apellido(faker.name().lastName());

            Especialidad espAleatoria = especialidadesGuardadas.get(faker.random().nextInt(especialidadesGuardadas.size()));
            staff.setNombreesp(espAleatoria);

            staffRepository.save(staff);
        }

        log.info("¡Carga masiva de 100 médicos completada con éxito!");
    }
}