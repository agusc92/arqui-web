package org.example;

import java.util.List;
import java.util.Optional;

/*! \brief
 * interfaz que define los metodos a implementar para satisfacer los requerimienros
 *
 *
 * @author Agustin La Battaglia
 * @version 1.0
 *
 */
public interface Dao<T> {

    /**
     * devuelve un elemento solicitado a traves de el identificador
     * @param id identificador de un elemento
     * @return devuelve una instancia de el elemento
     * @see User
     * @see User#getId()
     */
    Optional<T> get(int id);

    /**
     * devuelve una lista con todos los elementos
     * @return lista con todos los elementos
     */
    List<T> getAll();

    /**
     * agrega un elemento a la base de datos
     * @param t elemento a agregar
     */
    void add(T t);

    /**
     * borra un elemento de la base de datos
     * @param id identificador de el elemento a borrar
     */
    void delete(int id);

    /**
     * actualiza la informacion de un elemento
     * @param t elemento actualizado
     */
    void update(T t);
}
