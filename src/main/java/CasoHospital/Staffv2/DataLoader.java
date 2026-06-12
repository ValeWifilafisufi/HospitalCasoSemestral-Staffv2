package CasoHospital.Staffv2;

import CasoHospital.Staffv2.model.Especialidad;
import CasoHospital.Staffv2.model.Staff;
import CasoHospital.Staffv2.repository.EspecialidadRepository;
import CasoHospital.Staffv2.repository.StaffRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private final StaffRepository staffRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    public void run(String... args) {
        if (staffRepository.count() > 0) {
            log.info("La BD de Staff ya tiene datos.");
            return;
        }

        Faker faker = new Faker(new Locale("es"));
        List<Especialidad> especialidadesGuardadas = new ArrayList<>();

        for (long i = 1; i <= 10; i++) {
            Especialidad esp = new Especialidad();
            esp.setCod_especialidad(i);
            esp.setNombreesp("Especialidad " + faker.medical().diseaseName() + " " + i);
            especialidadesGuardadas.add(especialidadRepository.save(esp));
        }

        for (int i = 0; i < 30; i++) {

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

        log.info("Datos falsos de Staff y Especialidad insertados correctamente con Faker.");
    }
}

