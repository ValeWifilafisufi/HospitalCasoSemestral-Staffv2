package CasoHospital.Staffv2.config;

import CasoHospital.Staffv2.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInicializer implements CommandLineRunner {
    private final StaffRepository staffRepository;

    @Override
    public void run(String... args) {

        if (staffRepository.count() > 0) {
            log.info("La BD ya tiene datos");
            return;
        }

        log.info("BD vacia");
    }
}
