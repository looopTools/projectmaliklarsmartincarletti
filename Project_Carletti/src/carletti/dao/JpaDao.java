/**
 * @author Lars Nielse, Malik L. Lund, Matrin R. Bungaard
 * @class JpaDao
 * @programmer Lars Nielsen
 */
package carletti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;

public class JpaDao implements Dao{

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

	@Override
	public List<SubProduct> getSubProducts(State state) {
		Query query = em.createQuery("SELECT sp FROM SubProduct sp WHERE sp.state = :state");
		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state) {
//		Query query = em.createQuery("SELECT sp FROM SubProduct sp WHERE sp.id = :id", SubProduct.class);
//		query.setParameter("id", subProduct.getId());
//		SubProduct sp = (SubProduct)query.getResultList().get(0);
		em.getTransaction().begin();
		subProduct.setState(state);
		em.getTransaction().commit();
		return subProduct;
	}

	/**
	 * 
	 */
	@Override
	public void storeProduct(Product p) {
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
	}

	@Override
	public void removeProduct(Product p) {
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
	}

	@Override
	public void removeSubProduct(SubProduct sp) {
		em.getTransaction().begin();
		em.remove(sp);
		em.getTransaction().commit();
	}

	@Override
	public List<Product> getProducts() {
		Query query = em.createQuery("SELECT p FROM Product p", Product.class);
		return query.getResultList();
	}

	@Override
	public List<SubProduct> getSubProducts() {
		Query query = em.createQuery("SELECT sp FROM SubProduct sp", SubProduct.class);
		return query.getResultList();
	}

	@Override
	public int getSubProducNextID() {
		return 0;
	}

	@Override
	public void countSubProducID() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getProducNextID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void countProducID() {
		// TODO Auto-generated method stub
		
	}
}
