package org.example.dao;

import org.example.entities.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDAO {
    private Connection conn;

    public PersonaDAO(Connection conn ){
        this.conn = conn;
    }

    public void insertPersona(Persona persona){
        String query = "INSERT INTO persona(id , nombre, edad) VALUES(?,?,?)";
        PreparedStatement ps = null;

        try{
            ps= this.conn.prepareStatement(query);
            ps.setInt(1,persona.getId());
            ps.setString(2,persona.getNombre());
            ps.setInt(3,persona.getEdad());
            ps.executeUpdate();
            System.out.println("persona insertada");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(ps!=null){
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Persona find(Integer pk) {
        String query = "SELECT p.nombre, p.edad" +
                "FROM Persona p " +
                "WHERE p.idPersona = ?";
        Persona personaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, pk); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");


                // Crear una nueva instancia de Persona con los datos recuperados de la consulta
                personaById = new Persona(pk, nombre, edad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return personaById;
    }
}
