package modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.namespace.QName;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name="turno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turno {

   @Id
    private int id;

   @Column (name = "fecha")
   private LocalDate dateTime;

    @ManyToMany(mappedBy = "turnos")
    private List<Persona> personas;
}
