package modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Socio {

    @Id
    private int id;

    @Column
    private String tipo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id") // O "persona_id" si tu FK se llama así
    private Persona persona;

    public Socio(String tipo, Persona persona) {
        this.tipo = tipo;
        this.persona = persona;
    }
}
