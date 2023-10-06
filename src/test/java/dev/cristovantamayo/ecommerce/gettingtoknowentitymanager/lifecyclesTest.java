package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Category;
import org.junit.Test;

public class lifecyclesTest extends EntityManagerTest {

    @Test
    public void statesAnalysis() {

        Category transientObject = new Category();

        // if persist it go to managed state
        entityManager.persist(transientObject);
        // Or
        Category newManagedObject = entityManager.merge(transientObject);

        Category managedObject = entityManager.find(Category.class, 1);

        //It's going to be removed state
        entityManager.remove(managedObject);

        // if persist it go to managed state
        entityManager.persist(managedObject);

        // Detached state
        entityManager.detach(managedObject);


    }
}
