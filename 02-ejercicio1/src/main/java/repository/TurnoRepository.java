package repository;

import Factory.JPAUtil;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import modelo.Domicilio;
import modelo.Persona;
import modelo.Turno;

import java.io.FileReader;
import java.time.LocalDate;

public class TurnoRepository {
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Turno turno = new Turno();
                turno.setId(Integer.parseInt(linea[0]));
                turno.setDateTime(LocalDate.parse(linea[1]));





                em.persist(turno);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


}
