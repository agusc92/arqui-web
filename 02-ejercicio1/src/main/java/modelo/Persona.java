package modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "nombre")
    private String nombre;

    @Column (name = "edad")
    private int anios;

    @OneToOne(mappedBy = "persona")
    private Socio socio;

    @ManyToOne
    @JoinColumn (name = "direccion_id")
    private Domicilio domicilio;

    @ManyToMany
    @JoinTable(
            name = "persona_turno", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "persona_id"), // FK de esta entidad
            inverseJoinColumns = @JoinColumn(name = "turno_id") // FK de la otra entidad
    )
    private List<Turno> turnos;


}
