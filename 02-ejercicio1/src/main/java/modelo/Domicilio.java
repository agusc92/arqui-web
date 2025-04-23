package modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name = "domicilio")
@Getter
@Setter

@AllArgsConstructor
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "ciudad")
    private String ciudad;

    @Column (name = "calle")
    private String calle;



    @OneToMany (mappedBy = "domicilio")
    private List<Persona> personas;

    public Domicilio(){
        super();
        this.personas = new ArrayList<Persona>();
    }



}
