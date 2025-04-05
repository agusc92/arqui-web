package org.example;

/*! \brief
 * define el elemeto usuario
 *
 * a ver esto
 *
 * @author agustin
 * @version 1.0
 */
public class User {
    //campos de la clase
    private int id;
    private String nombre;
    private int edad;

    /**
     * constructor para la clase persona
     * @param id identificador de la persona
     * @param nombre nombre de la persona
     * @param edad edad de la persona
     */
    public User(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    } //cierre de constructor

    /**
     *
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
