package CasoHospital.Staffv2.config;

import CasoHospital.Staffv2.repository.StaffRepository;


public class DataInicializer {

    private final StaffRepository staffRepository;

    @Override
    public void run(String... args) {
        if (staffRepository.count() > 0) {
            lon.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        log.info(">>>DataInitializer: BD vacia detextada, insertando datos de prueba...");


        log.info(">>>DataInitializer: {} habitaciones insertadas correctamente.",
                habitacionRepository.count());
    }
}
