package repository;

import Factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Domicilio;
import modelo.Persona;
import modelo.Socio;
import modelo.Turno;

public class SocioRepository {
    /*
    Preguntar si: Existe un registro en la tabla socio por cada registro en la tabla persona.
    De ser asi, preguntar si la insercion en la tabla socio es responsabilidad de el insert de persona
        o de un trigger en la tabla persona que lo cree automatico.
     */
    public void modificarEstado(int id, String estado){

            EntityManager em = JPAUtil.getEntityManager();
            em.getTransaction().begin();

            Socio socio = em.find(Socio.class, id);
            if (socio != null) {

                if (socio.getTipo() != null) {
                    socio.setTipo(estado);
                }
                // No hace falta llamar a merge() porque la entidad ya está "managed"
            }

            em.getTransaction().commit();

    }
    public void insertar(Socio soc,EntityManager em){


        em.persist(soc);

    }
    public void eliminar(int id, EntityManager em){


        Socio soc = em.find(Socio.class, id);// Buscamos el objeto por ID
        if (soc != null) {
            em.remove(soc); // Lo eliminamos si existe
        }


    }
}
