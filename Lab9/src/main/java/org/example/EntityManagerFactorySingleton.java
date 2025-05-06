package org.example;
import jakarta.persistence.*;



public class EntityManagerFactorySingleton {

    private static EntityManagerFactory emf;

    private EntityManagerFactorySingleton() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (EntityManagerFactorySingleton.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("ExamplePU");
                }
            }
        }
        return emf;
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
