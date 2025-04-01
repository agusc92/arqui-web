package agus.db;



import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDatos {
    public static void main(String[] args)  {
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | ClassNotFoundException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);

        }

        String uri ="jdbc:mysql://localhost:3306/testdb";
        try {
            Connection con = DriverManager.getConnection(uri,"root","");
            con.setAutoCommit(false);
            createTables(con);
//            addPerson(con,1,"pedro",30);
//            addPerson(con,2,"alejandra",20);
//            addCurso(con,1,"arqui-web",1);

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
    private static void addCurso(Connection con,int id, String nombre, int id_persona) throws SQLException {
        String insert = "INSERT INTO curso(id,nombre,id_persona) VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setInt(1,id);
        ps.setString(2,nombre);
        ps.setInt(3,id_persona);
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
        /*String table = "CREATE TABLE curso ("
                + "id INT PRIMARY KEY, "
                + "nombre VARCHAR(500), "
                + "id_persona INT, "
                + "FOREIGN KEY (id_persona) REFERENCES persona(id) ON DELETE CASCADE"
                + ")";
                */

        con.prepareStatement(table).execute();
        con.commit();

    }
}
