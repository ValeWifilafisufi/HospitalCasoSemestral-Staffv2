package CasoHospital.Staffv2.dtos;

import CasoHospital.Staffv2.model.Especialidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StaffResponseDTO {

    private Long num_registro;
    private String numrun;
    private String nombre;
    private String p_apellido;
    private String m_apellido;
    private String nombreesp;
}
