package dev.cristovantamayo.ecommerce.iniciandocomjpa;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TransactionsWithOperationsTest extends EntityManagerTest {
    @Test
    public void insertObjectWithMergeMethod() {
        Produto product = new Produto();
        product.setId(4);
        product.setNome("Microphone Rode Videmic");
        product.setDescricao("A melhor qualidade de som.");
        product.setPreco(new BigDecimal(1000));

        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto actualProduct =
                entityManager.find(Produto.class, product.getId());

        Assert.assertNotNull(actualProduct);
    }

    @Test
    public void updateManagedObject() {
        Produto product = entityManager.find(Produto.class, 1);


        entityManager.getTransaction().begin();
        product.setNome("Kindle Paperwhite Next Generation");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto actualProduct =
                entityManager.find(Produto.class, product.getId());

        Assert.assertEquals("Kindle Paperwhite Next Generation", actualProduct.getNome());
    }

    @Test
    public void updateObject() {
        Produto product = new Produto();
        product.setId(1);
        product.setNome("Kindle Paperwhite");
        product.setDescricao("Conheça o novo Kindle Paperwhite");
        product.setPreco(new BigDecimal(599));

        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto actualProduct =
                entityManager.find(Produto.class, product.getId());

        Assert.assertNotNull(actualProduct);
        Assert.assertEquals("Kindle Paperwhite", actualProduct.getNome());
    }

    @Test
    public void removeObject() {

        Produto product = entityManager.find(Produto.class, 3);

        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();

        // No necessary here, because remove method clear entityManager memory under de hood
        // entityManager.clear();

        Produto actualProduct =
                entityManager.find(Produto.class, 3);

        Assert.assertNotNull(actualProduct);
    }

    @Test
    public void insertFirstObject() {
        Produto product = new Produto();
        product.setId(2);
        product.setNome("Câmera Canon");
        product.setDescricao("A melhor definição para suas Fotos.");
        product.setPreco(new BigDecimal(5000));

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto actualProduct =
                entityManager.find(Produto.class, product.getId());

        Assert.assertNotNull(actualProduct);
    }

    public void openAndCloseTransaction() {

        Produto product = new Produto();

        entityManager.getTransaction().begin();

//        entityManager.persist(product);
//        entityManager.merge(product);
//        entityManager.remove(product);

        entityManager.getTransaction().commit();

    }
}
