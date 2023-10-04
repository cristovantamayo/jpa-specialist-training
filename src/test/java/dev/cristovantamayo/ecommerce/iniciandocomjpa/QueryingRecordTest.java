package dev.cristovantamayo.ecommerce.iniciandocomjpa;

import dev.cristovantamayo.ecommerce.model.Produto;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class QueryingRecordTest {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        entityManagerFactory.close();;
    }

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.close();
    }

    @Test
    public void searchForObjectIdentifier() {
        Produto product =
                entityManager.find(Produto.class, 1);

        Produto productRef =
                entityManager.getReference(Produto.class, 1);

        System.out.println(productRef);

        Assert.assertNotNull(product);
        Assert.assertEquals("Kindle", product.getNome());
    }

    @Test
    public void UpdateProductReferenceTest() {
        Produto product =
                entityManager.find(Produto.class, 1);

        product.setNome("Microphone Samsung");

        // refresh restore object attributes from database
        entityManager.refresh(product);

        Assert.assertEquals("Kindle", product.getNome());
    }
}
