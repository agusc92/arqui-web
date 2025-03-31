package agus.db;

import org.apache.derby.jdbc.EmbeddedDriver;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDatos {
    public static void main(String[] args)  {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String uri ="jdbc:derby:myDerbyDB;create=true";
        try {
            Connection con = DriverManager.getConnection(uri);
            createTables(con);
            addPerson(con,1,"pedro",30);
            addPerson(con,2,"alejandra",20);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addPerson(Connection con, int id, String nombre, int years) throws SQLException {
        String insert = "INSERT INTO persona(id,nombre,edad) VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setInt(1,id);
        ps.setString(2,nombre);
        ps.setInt(3,years);
        ps.executeUpdate();
        ps.close();
        con.commit();
    }

    private static void createTables(Connection con) throws SQLException {
        String table = "CREATE TABLE persona("
        + "id INT,"+
         "nombre VARCHAR(500),"+
         "edad INT,"+
                "PRIMARY KEY (id))";
        con.prepareStatement(table).execute();
        con.commit();

    }
}
