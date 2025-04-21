package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.PersonaDAO;
import org.example.entities.Persona;
import org.example.factory.AbstractFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Helper {
    private Connection conn;

    public Helper(){
        String driver="com.mysql.cj.jdbc.Driver";
        String uri="jdbc:mysql://localhost:3306/testdb";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try{
            conn = DriverManager.getConnection(uri,"root","");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src\\main\\resources\\"+ archivo;
        Reader in = new FileReader(path);
        String[]  header ={};
        CSVParser csvParser = CSVFormat.EXCEL.withHeader(header).parse(in);
        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }


    public void populateDB(PersonaDAO perDao) throws Exception {
        System.out.println("Populating DB...");

        for (CSVRecord row : getData("personas.csv")) {
            if (row.size() >= 4) { // Verificar que hay al menos 4 campos en el CSVRecord
                String idString = row.get(0);
                String nombre = row.get(1);
                String edadString = row.get(2);


                if (!idString.isEmpty() && !nombre.isEmpty() && !edadString.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idString);
                        int edad = Integer.parseInt(edadString);


                        Persona persona = new Persona(id, nombre, edad);
                        perDao.insertPersona(persona);

                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en datos de persona: " + e.getMessage());
                    }
                }
            }
        }

        System.out.println("Personas insertadas");

    }
}
