package org.example;

import org.example.dao.PersonaDAO;
import org.example.entities.Persona;
import org.example.factory.AbstractFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        AbstractFactory chosenFactory =AbstractFactory.getDAOFactory(1);

        PersonaDAO perDao = chosenFactory.getPersonaDAO();
        Helper helpPersona = new Helper();
        helpPersona.populateDB(perDao);

        Persona personaId = perDao.find(1);
        System.out.println(personaId);

    }
}