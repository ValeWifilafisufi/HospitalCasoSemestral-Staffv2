package CasoHospital.Staffv2.dtos;

import CasoHospital.Staffv2.model.Especialidad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StaffRequestDTO {

    @NotNull(message = "El numero de rut no puede ser nulo")
    @Positive(message = "El numero de rut debe ser mayor a 0")
    private Long num_registro;

    @NotNull(message = "El numero de rut no puede estar vacio")
    @Positive(message = "El numero de rut debe ser positivo")
    private Long numrun;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido paterno no puede estar vacio")
    private String p_apellido;

    @NotBlank(message = "El apellido materno no puede estar vacio")
    private String m_apellido;

    @NotNull(message = "El codigo de  especialidad no puede estar vacio")
    @Positive(message = "El codigo de la especialidad debe ser mayor que 0")
    private Long cod_especialidad;


}
