package CasoHospital.Staffv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff_medico")

public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num_registro;

    @Column(name = "run", nullable = false, unique = true)
    private String numrun;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = false)
    private String p_apellido;

    @Column(name = "apellido_materno", nullable = false)
    private String m_apellido;

    @ManyToOne
    @JoinColumn(name = "cod_especialidad", nullable = false)
    private Especialidad nombreesp;
}
