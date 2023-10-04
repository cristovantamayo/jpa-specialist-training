package dev.cristovantamayo.ecommerce.util;

import dev.cristovantamayo.ecommerce.model.Produto;

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
        Produto produto = entityManager.find(Produto.class, 1);
        System.out.println(produto.getNome());
        System.out.println(produto.getDescricao());
        System.out.println(produto.getPreco());

        entityManager.close();
        entityManagerFactory.close();
    }
}
