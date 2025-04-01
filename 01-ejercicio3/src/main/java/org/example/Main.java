package org.example;

import java.util.ArrayList;
import java.util.Optional;

public class Main {
    public static void main(String[] args)  {
        User usuario1 = new User(1,"Agustin",33);
        User usuario2 = new User(2,"Luciana",33);
        User usuario3 = new User(3,"Nicolas",33);

        UserDao uD = new UserDao("jdbc:mysql://localhost:3306/testdb","com.mysql.cj.jdbc.Driver");

        ArrayList<User> usuarios = (ArrayList<User>) uD.getAll();

        for (User usuario : usuarios) {
            System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Edad: " + usuario.getEdad());
        }



    }
}