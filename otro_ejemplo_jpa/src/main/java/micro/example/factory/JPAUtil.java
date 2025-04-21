package micro.example.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JPAUtil {
    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("otroejemplojpa");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

