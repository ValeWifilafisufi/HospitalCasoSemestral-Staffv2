package CasoHospital.Staffv2.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos del staff creado/actualizado")
public class StaffResponseDTO {

    @Schema(description = "Numero de registro unico", example = "1")
    private Long num_registro;

    @Schema(description = "Run del funcionario", example = "11.111.111-1")
    private String numrun;

    @Schema(description = "Nombre del funcionario", example = "Matias")
    private String nombre;

    @Schema(description = "Apellido paterno del funcionario", example = "Beas")
    private String p_apellido;

    @Schema(description = "Apellido materno del funcionario", example = "Beas")
    private String m_apellido;

    @Schema(description = "Nombre de la especialidad del funcionario", example = "Cardiologia")
    private String nombreesp;
}
