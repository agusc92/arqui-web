package agus.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOLocalConection {
    private String uri;
    private String user;
    private String password;
    public String driver;

    public DAOLocalConection(String uri, String user, String password, String driver) throws SQLException, ClassNotFoundException {
        this.uri = uri;
        this.user = user;
        this.password = password;
        this.driver = driver;
        this.createDriver();
    }
    public DAOLocalConection(String uri, String drver) throws SQLException, ClassNotFoundException {
        this.uri = uri;
        this.driver = drver;
        this.user = null;
        this.password = null;
        this.createDriver();
    }

    private void createDriver() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(this.driver).getDeclaredConstructor().newInstance();
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
    }
    public void insertar(Usuario usuario) throws SQLException {
        try {
            Connection con = DriverManager.getConnection(this.uri,"root","admin");
            con.setAutoCommit(false);
            String insert = "INSERT INTO persona(id,nombre,edad) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setInt(1,usuario.getId());
            ps.setString(2, usuario.getNombre());
            ps.setInt(3,usuario.getEdad());
            ps.executeUpdate();
            ps.close();
            con.commit();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
