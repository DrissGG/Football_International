/**
 * 
 */
package foot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/**
 * 
 */
public class JPAUtils {
	private final static JPAUtils INSTANCE = new JPAUtils();
	private JPAUtils() {}
	public static JPAUtils getInstance() {
		return INSTANCE;
	}
	
	//JPA
	private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("projet-jpa");
	private final static EntityManager entityManager = entityManagerFactory.createEntityManager();		
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
