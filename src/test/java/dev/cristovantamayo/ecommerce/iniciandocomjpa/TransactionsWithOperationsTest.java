package dev.cristovantamayo.ecommerce.iniciandocomjpa;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TransactionsWithOperationsTest extends EntityManagerTest {

    @Test
    public void preventDatabaseOperation() {
        Product product = entityManager.find(Product.class, 1);

        entityManager.detach(product);

        entityManager.getTransaction().begin();
        product.setName("Kindle Paperwhite Next Generation");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct =
                entityManager.find(Product.class, product.getId());

        Assert.assertEquals("Kindle", actualProduct.getName());
    }

    @Test
    public void showDiferenceBetweenPersistAndMerge() {

        Product productPersist = new Product();
        productPersist.setName("Microphone Rode Videmic");
        productPersist.setDescription("A melhor qualidade de som.");
        productPersist.setPrice(new BigDecimal(1000));

        entityManager.getTransaction().begin();
        entityManager.persist(productPersist);
        productPersist.setName("SmartPhone Samsung S23");
        productPersist.setPrice(new BigDecimal(8000));
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProductPersist =
                entityManager.find(Product.class, productPersist.getId());

        Assert.assertNotNull(actualProductPersist);


        Product productMerge = new Product();
        productMerge.setName("Notebook Dell");
        productMerge.setDescription("O melhor da categoria.");
        productMerge.setPrice(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        productMerge = entityManager.merge(productMerge);
        productMerge.setName("Notebook Dell Inspire");
        productMerge.setPrice(new BigDecimal(2500));
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProductMerge =
                entityManager.find(Product.class, productMerge.getId());

        Assert.assertNotNull(actualProductMerge);
    }
    @Test
    public void insertObjectWithMergeMethod() {
        Product product = new Product();
        product.setName("Microphone Rode Videmic");
        product.setDescription("A melhor qualidade de som.");
        product.setPrice(new BigDecimal(1000));

        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct =
                entityManager.find(Product.class, product.getId());

        Assert.assertNotNull(actualProduct);
    }

    @Test
    public void updateManagedObject() {
        Product product = entityManager.find(Product.class, 1);


        entityManager.getTransaction().begin();
        product.setName("Kindle Paperwhite Next Generation");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct =
                entityManager.find(Product.class, product.getId());

        Assert.assertEquals("Kindle Paperwhite Next Generation", actualProduct.getName());
    }

    @Test
    public void updateObject() {
        Product product = new Product();
        product.setName("Kindle Paperwhite");
        product.setDescription("Conheça o novo Kindle Paperwhite");
        product.setPrice(new BigDecimal(599));

        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct =
                entityManager.find(Product.class, product.getId());

        Assert.assertNotNull(actualProduct);
        Assert.assertEquals("Kindle Paperwhite", actualProduct.getName());
    }

    @Test
    public void removeObject() {

        Product product = entityManager.find(Product.class, 3);

        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();

        // No necessary here, because remove method clear entityManager memory under de hood
        // entityManager.clear();

        Product actualProduct =
                entityManager.find(Product.class, 3);

        Assert.assertNull(actualProduct);
    }

    @Test
    public void insertFirstObject() {
        Product product = new Product();
        product.setName("Câmera Canon");
        product.setDescription("A melhor definição para suas Fotos.");
        product.setPrice(new BigDecimal(5000));

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct =
                entityManager.find(Product.class, product.getId());

        Assert.assertNotNull(actualProduct);
    }

    public void openAndCloseTransaction() {

        Product product = new Product();

        entityManager.getTransaction().begin();

//        entityManager.persist(product);
//        entityManager.merge(product);
//        entityManager.remove(product);

        entityManager.getTransaction().commit();

    }
}
