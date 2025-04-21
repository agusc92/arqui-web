package org.example.factory;

import org.example.dao.PersonaDAO;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @brief establece una coneccion a la base de datos MySQL
 *
 * Utilizando el patron de desarrollo Singleton se asegura de que solo exista
 * un solo punto de acceso a la base de datos.
 * tambien se encarga de instanciar El DAO de las entidades de este esquema.
 *
 * @author Agustin
 * @version 1.0.0
 */


public class MysqlDAOFactory extends AbstractFactory {
    public static final String DRIVER ="com.mysql.cj.jdbc.Driver";
    public static final String URI="jdbc:mysql://localhost:3306/testdb";
    public static Connection conn;
    private static MysqlDAOFactory instance = null;

    /**
     * constructor privado
     */
    private MysqlDAOFactory(){};

    /**
     * junto al constructor privado completan el patron Singleton,
     * devolviendo la instancia si existe o creandola en caso de no existir.
     *
     * @return una instancia de @see MysqlDAOFactory
     */
    public static synchronized MysqlDAOFactory getInstance(){
        if(instance == null){
            instance = new MysqlDAOFactory();
        }
        return instance;
    }

    /**
     * crea una coneccion de ser necesario o utiliza la existente
     * @return coneccion a MySQL
     */
    public static Connection CreateConnection(){
        if(conn != null){
            return conn;
        }
        String driver = DRIVER;
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try{
            conn = DriverManager.getConnection(URI,"root","");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * Cierra la coneccion
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * crea y retorna una instancia de PersonaDAO
     * @return @see PersonaDAO
     */
    @Override
    public PersonaDAO getPersonaDAO() {
        return new PersonaDAO(conn);
    }
}
