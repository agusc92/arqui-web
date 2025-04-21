import Factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Domicilio;
import modelo.Persona;
import repository.DomicilioRepository;
import repository.PersonaRepository;
import repository.TurnoRepository;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[]args){
        EntityManager em = JPAUtil.getEntityManager();
        PersonaRepository pr = new PersonaRepository();
        DomicilioRepository dr = new DomicilioRepository();
        TurnoRepository tr = new TurnoRepository();

        dr.insertarDesdeCSV("src/main/resources/direccion.csv");
        pr.insertarDesdeCSV("src/main/resources/persona.csv");
        tr.insertarDesdeCSV("src/main/resources/turno.csv");

        // pruevas sobre la tabla domicilio
        Domicilio dom =new Domicilio();
        dom.setCalle("81");
        dom.setCiudad("necochea");
        dr.insertar(dom);
        dr.eliminar(7);

        Domicilio dom2 =new Domicilio();
        dom2.setId(4);
        dom2.setCiudad("necochea");
        dom2.setCalle("75");
        dr.actualizar(dom2);

        System.out.println(dr.buscarPorId(4));

        //pruevas sobre la tabla persona

        Persona per = new Persona();
        per.setNombre("Agustin");
        per.setAnios(33);
        per.setDomicilio(dr.find(4));
        pr.insertar(per);

        pr.eliminar(4);
        System.out.println(pr.buscarPorId(5));
    }
}
