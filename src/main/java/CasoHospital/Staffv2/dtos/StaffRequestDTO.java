package CasoHospital.Staffv2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StaffRequestDTO {

    @NotBlank(message = "El numero de rut no puede estar vacio")
    @Pattern(
            regexp = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9kK]$",
            message = "El run debe tener formato 12.345.678-9"
    )
    private String numrun;

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
