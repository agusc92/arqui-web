package org.example.factory;

import org.example.dao.PersonaDAO;

/**
 * @brief permite crear distintos factorys
 *
 * mediante un parametro se podra elegir que factory se desea crear
 *
 * @author Agustin La Battaglia
 * @version 1.0.0
 */
public abstract class  AbstractFactory {
    public static final int MYSQL_JDBC = 1;

    /**
     * declaracion de metodo para obtener un Dao de persona
     * @return @see PersonaDAO
     */
    public abstract PersonaDAO getPersonaDAO();

    /**
     *
     * @param choise int para seleccionar el factory que se quiere crear
     * @return Uba instancia de el factory elegido
     */
    public static AbstractFactory getDAOFactory(int choise){
        switch (choise){
            default : return MysqlDAOFactory.getInstance();
        }
    }

}
