package CasoHospital.Staffv2.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Datos necesarios para la creacion o actualizacion de un staff")
public class StaffRequestDTO {

    @Schema(description = "Run del funcionario", example = "11.111.111-K")
    @NotBlank(message = "El numero de rut no puede estar vacio")
    @Pattern(
            regexp = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9kK]$",
            message = "El run debe tener formato 12.345.678-9"
    )
    private String numrun;

    @Schema(description = "Nombre del funcionario", example = "Matias")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @Schema(description = "Apellido paterno del funcionario", example = "Beas")
    @NotBlank(message = "El apellido paterno no puede estar vacio")
    private String p_apellido;

    @Schema(description = "Apellido materno del funcionario", example = "Beas")
    @NotBlank(message = "El apellido materno no puede estar vacio")
    private String m_apellido;

    @Schema(description = "Codigo de la especialidad del funcionario", example = "101")
    @NotNull(message = "El codigo de  especialidad no puede estar vacio")
    @Positive(message = "El codigo de la especialidad debe ser mayor que 0")
    private Long cod_especialidad;

}
