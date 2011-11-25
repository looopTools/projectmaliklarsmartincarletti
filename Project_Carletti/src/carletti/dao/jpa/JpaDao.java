/**
 * @author Lars Nielse, Malik L. Lund, Matrin R. Bungaard
 * @class JpaDao
 * @programmer Lars Nielsen
 */
package carletti.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import carletti.model.SubProduct;

public class JpaDao {

	private static JpaDao uniqueInstance;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	private JpaDao(){
		emf = Persistence.createEntityManagerFactory("Carletti");
		em = emf.createEntityManager();
	}
	
	/**
	 * insures that we are only able to create on JpaDao instance
	 * and it is done, but implementing the Singelton design pattern
	 * @return
	 */
	public static JpaDao getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new JpaDao();
		}
		
		return uniqueInstance;
	}
	
	
	public void storeSubProduct(SubProduct sp){
		em.getTransaction().begin();
		em.persist(sp);
		em.getTransaction().commit();
	}
	
	
	
	public void close(){
		em.close();
		emf.close();
	}
}
