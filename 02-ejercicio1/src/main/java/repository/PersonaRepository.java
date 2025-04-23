package repository;

import Factory.JPAUtil;
import com.opencsv.CSVReader;
import dto.DomicilioDTO;
import dto.PersonaDTO;
import jakarta.persistence.EntityManager;
import modelo.Domicilio;
import modelo.Persona;
import modelo.Socio;
import modelo.Turno;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PersonaRepository {

    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();
        SocioRepository sr = new SocioRepository();
        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Persona persona = new Persona();
                persona.setNombre(linea[1]);
                persona.setAnios(Integer.parseInt(linea[2]));


                // Busco la Direccion por ID
                Domicilio direccion = em.find(Domicilio.class, Integer.parseInt(linea[3]));
                persona.setDomicilio(direccion);

                persona.setTurnos(new ArrayList<Turno>());

                em.persist(persona);
                System.out.println(persona.getId());
                em.flush();
                sr.insertar(new Socio("no-socio",persona),em);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void insertar(Persona per){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(per);
        em.getTransaction().commit();
    }
    public void eliminar(int id){
        SocioRepository sr = new SocioRepository();
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Persona per = em.find(Persona.class, id);// Buscamos el objeto por ID
        sr.eliminar(per.getId(),em);
        if (per != null) {
            em.remove(per); // Lo eliminamos si existe
        }

        em.getTransaction().commit();
    }

    public void actualizar(Persona per) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(per); // Actualiza si existe, inserta si no está en contexto
        em.getTransaction().commit();
    }

    public void actualizarParcial(int id, String nuevoVombre, int nuevoAnio) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

       Persona per = em.find(Persona.class, id);
        if (per != null) {
            if (nuevoVombre != null) {
                per.setNombre(nuevoVombre);
            }
            if (nuevoAnio >=0 ) {
                per.setAnios(nuevoAnio);
            }
            // No hace falta llamar a merge() porque la entidad ya está "managed"
        }

        em.getTransaction().commit();
    }
    public PersonaDTO buscarPorId(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        PersonaDTO perDto = new PersonaDTO();
        Persona per =  em.find(Persona.class, id);
        perDto.setNombre(per.getNombre());
        perDto.setEdad(per.getAnios());
        return perDto;
    }

    public Persona find(int id){
        EntityManager em = JPAUtil.getEntityManager();
        return em.find(Persona.class,id);
    }
    public void asignarTurno(Turno tur,Persona per){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Persona p3 = em.find(Persona.class,per.getId());
        Turno t1 = em.find(Turno.class,tur.getId());

        if(p3 != null && t1 != null){
            p3.getTurnos().add(t1);
            t1.getPersonas().add(p3);

            em.merge(p3);
            em.merge(t1);
        }

        em.getTransaction().commit();
    }

    public ArrayList<PersonaDTO> obtenerPersonasPorTurno(int idTurno) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Persona> personas = em.createQuery(
                        "SELECT p FROM Persona p JOIN p.turnos t WHERE t.id = :idTurno", Persona.class)
                .setParameter("idTurno", idTurno)
                .getResultList();
        em.close();

        ArrayList<PersonaDTO> personaDTO = new ArrayList<>();
        for(Persona p : personas){
            personaDTO.add(generarDTO(p));
        }
        return personaDTO;
    }

    private PersonaDTO generarDTO(Persona p){
        String ciudad = (p.getDomicilio() != null) ? p.getDomicilio().getCiudad() : "Sin ciudad";
        String calle = (p.getDomicilio() != null) ? p.getDomicilio().getCalle() : "Sin calle";
        String tipoSocio = (p.getSocio() != null) ? p.getSocio().getTipo() : "No socio";

        return new PersonaDTO(p.getNombre(), p.getAnios(), ciudad, calle, tipoSocio);
    }
}
