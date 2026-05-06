package CasoHospital.Staffv2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table


public class Especialidad {
    @Id
    private Long cod_especialidad;

    @Column(nullable = false, unique = true)
    private String nombreesp;
}
