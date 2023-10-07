package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Test;

public class FirstLevelCacheTest extends EntityManagerTest {

    @Test
    public void cacheValidation() {

        Product product = entityManager.find(Product.class, 1);
        System.out.println(product.getName());
        System.out.println("------------------------------------");
        Product recoveredProduct = entityManager.find(Product.class, product.getId());
        System.out.println(recoveredProduct.getName());

    }
}
