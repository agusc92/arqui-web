package org.example;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User>{
    private final String uri;

    public UserDao(String uri, String driver){
        this.uri = uri;
        findDriver(driver);
    }
    //busco el driver, ya que esto se hace una sola vez
    private void findDriver(String driver) {
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | ClassNotFoundException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public Optional<User> get(int id) {
        try {
            Connection con = DriverManager.getConnection(uri,"root","");
            String select = "select * from user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(select);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                User userR = new User(rs.getInt(1),rs.getString(2),rs.getInt(3));
                return Optional.of(userR);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        ArrayList<User> list = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(uri,"root","");
            String select = "select * from user";
            PreparedStatement ps = con.prepareStatement(select);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                list.add( new User(rs.getInt(1),rs.getString(2),rs.getInt(3)));


            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void add(User user) {

        try {
            Connection con = DriverManager.getConnection(this.uri,"root","");
            con.setAutoCommit(false);
            String insert = "INSERT INTO user(id,nombre,edad) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setInt(1,user.getId());
            ps.setString(2,user.getNombre());
            ps.setInt(3,user.getEdad());
            ps.executeUpdate();
            ps.close();
            con.commit();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            Connection con = DriverManager.getConnection(this.uri,"root","");
            con.setAutoCommit(false);
            String delete = "DELETE FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(delete);
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
            con.commit();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection con = DriverManager.getConnection(this.uri,"root","");
            con.setAutoCommit(false);
            String delete = "UPDATE user SET nombre = ? , edad = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(delete);
            ps.setString(1,user.getNombre());
            ps.setInt(2,user.getEdad());
            ps.setInt(3,user.getId());
            ps.executeUpdate();
            ps.close();
            con.commit();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
