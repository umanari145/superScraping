
import com.superscraping.entity.DmmItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Norio
 */
public class EJBTest {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;
    
    @BeforeClass
    public static void initEntityManager(){
        emf = Persistence.createEntityManagerFactory("com_SuperScraping_jar_1.0-SNAPSHOTPU2");
        em = emf.createEntityManager();
    }
    
    @AfterClass
    public static void closeEntityManager(){
        em.close();
        emf.close();
    }
    
    @Before
    public void initTransaction(){
        tx = em.getTransaction();
    }
    
    @Test
    public void createEntity(){
        DmmItem product = new DmmItem();
        product.setProductName("sample1");
        tx.begin();
        em.persist(product);
        tx.commit();
    }
}
