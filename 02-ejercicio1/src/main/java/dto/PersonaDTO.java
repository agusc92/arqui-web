package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonaDTO {
    private String nombre;
    private int edad;
    private String ciudad;
    private String calle;
    private String socio;
}
