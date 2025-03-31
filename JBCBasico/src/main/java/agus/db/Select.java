package agus.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Select {
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
            String select = "select * from persona";
            PreparedStatement ps = con.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1)+", "+rs.getString(2)+", "+rs.getInt(3));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
