package dev.cristovantamayo.ecommerce.util;

import dev.cristovantamayo.ecommerce.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUnityInitialization {

    public static void main (String[] args){
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("Ecommerce-PU");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        // Testes Aqui!
        Product produto = entityManager.find(Product.class, 1);
        System.out.println(produto.getName());
        System.out.println(produto.getDescription());
        System.out.println(produto.getPrice());

        entityManager.close();
        entityManagerFactory.close();
    }
}
