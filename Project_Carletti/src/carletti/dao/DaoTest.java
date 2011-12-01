package carletti.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * @author Malik
 *
 */
public class DaoTest {
	Dao dao = JpaDao.getInstance();

	@Before
	public void setUp() throws Exception {
		dao = null;
		Thread.sleep(100);
		dao = JpaDao.getInstance();
	}
	
	@After
	/**
	 * 
	 */
	public void cleanUp(){
		for (SubProduct sp: dao.getSubProducts()){
			dao.removeSubProduct(sp);
		}
		
		for(Product p: dao.getProducts()){
			dao.removeProduct(p);
		}
		
		for (Treatment t: dao.getTreatments()){
			dao.removeTreatment(t);
		}
	}

	@Test
	public void storeTreatmentTest() {
		Treatment t1 = new Treatment("TestTreatment");
		t1.createSubTreatment("st1", 1000, 2000, 3000);
		t1.createSubTreatment("st2", 1000, 2000, 3000);
		dao.storeTreatment(t1);
		List<Treatment> treatments = dao.getTreatments();
		assertEquals("Size differs from 1", 1, treatments.size());
		assertEquals("Name differs", "TestTreatment", treatments.get(0).getName());
		Treatment retrievedTreatment = treatments.get(0);
		assertEquals("1st SubTreatmentDiffers", t1.getSubTreatments().get(0), retrievedTreatment.getSubTreatments().get(0));
	}
}
