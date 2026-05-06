package CasoHospital.Staffv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Staff medico")

public class staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num_registro;

    @Column(nullable = false, unique = false)
    private Long numrun;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String p_apellido;

    @Column(nullable = false)
    private String m_apellido;

    @ManyToOne
    @JoinColumn(name = "cod_especialidad", nullable = false)
    private Especialidad nombreesp;
}
