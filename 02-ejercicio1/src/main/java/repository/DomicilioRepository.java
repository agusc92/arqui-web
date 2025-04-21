package repository;

import Factory.JPAUtil;
import com.opencsv.CSVReader;
import dto.DomicilioDTO;
import jakarta.persistence.EntityManager;
import modelo.Domicilio;
import modelo.Persona;

import java.io.FileReader;

public class DomicilioRepository {


    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Domicilio domicilio = new Domicilio();
                domicilio.setCiudad(linea[1]);
                domicilio.setCalle((linea[2]));




                em.persist(domicilio);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }


    }
    public void insertar(Domicilio dom){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(dom);
        em.getTransaction().commit();
    }

    public void eliminar(int id){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Domicilio domicilio = em.find(Domicilio.class, id);// Buscamos el objeto por ID
        if (domicilio != null) {
            em.remove(domicilio); // Lo eliminamos si existe
        }

        em.getTransaction().commit();
    }

    //esta version reemplaza el registro en la base de datos pisandolo con un registro nuevo
    public void actualizar(Domicilio dom) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(dom); // Actualiza si existe, inserta si no está en contexto
        em.getTransaction().commit();
    }

    public void actualizarParcial(int id, String nuevaCiudad, String nuevaCalle) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Domicilio domicilio = em.find(Domicilio.class, id);
        if (domicilio != null) {
            if (nuevaCiudad != null) {
                domicilio.setCiudad(nuevaCiudad);
            }
            if (nuevaCalle != null) {
                domicilio.setCalle(nuevaCalle);
            }
            // No hace falta llamar a merge() porque la entidad ya está "managed"
        }

        em.getTransaction().commit();
    }

    public DomicilioDTO buscarPorId(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        DomicilioDTO domDto = new DomicilioDTO();
        Domicilio dom =  em.find(Domicilio.class, id);
        domDto.setCalle(dom.getCalle());
        domDto.setCiudad(dom.getCiudad());
        return domDto;
    }

    public Domicilio find(int id){
        EntityManager em = JPAUtil.getEntityManager();
        return em.find(Domicilio.class,id);
    }

}
