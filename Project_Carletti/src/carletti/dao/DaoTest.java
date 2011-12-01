package carletti.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.model.Position;
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
		
	}
	
	@After
	/**
	 * Delete everything before starting new test.
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
	
	@Test
	public void removeTreatmentTest(){
		Treatment t1 = new Treatment("TestTreatment");
		t1.createSubTreatment("st1", 1000, 2000, 3000);
		t1.createSubTreatment("st2", 1000, 2000, 3000);
		dao.storeTreatment(t1);
		dao.removeTreatment(t1);
		
		assertEquals("List of treatments not empty", 0, dao.getTreatments().size());
	}
	
	@Test
	public void storeProductTest(){
		Treatment t1 = new Treatment("TestTreatment");
		t1.createSubTreatment("st1", 1000, 2000, 3000);
		t1.createSubTreatment("st2", 1000, 2000, 3000);
		dao.storeTreatment(t1);
		Product p1 = new Product("Product1", "ProductDescription", t1);
		dao.storeProduct(p1);
		List<Product> products = dao.getProducts();
		
		assertEquals("Size is not 1", 1, products.size());
		
		Product retrievedProduct = dao.getProducts().get(0);
		
		assertEquals("Products differs", p1, retrievedProduct);
		
		assertEquals("Treatment differs", t1, retrievedProduct.getTreatment());
	}
	
	@Test
	public void removeProductTest(){
		Treatment t1 = new Treatment("TestTreatment");
		t1.createSubTreatment("st1", 1000, 2000, 3000);
		t1.createSubTreatment("st2", 1000, 2000, 3000);
		Product p1 = new Product("Product1", "ProductDescription", t1);
		dao.storeProduct(p1);
		dao.removeProduct(p1);
		
		assertEquals("Product not deleted", 0, dao.getProducts().size());
	}
	
	@Test
	public void storeSubProductTest(){
		Treatment t1 = new Treatment("TestTreatment");
		t1.createSubTreatment("st1", 1000, 2000, 3000);
		t1.createSubTreatment("st2", 1000, 2000, 3000);
		Product p1 = new Product("Product1", "ProductDescription", t1);
		dao.storeProduct(p1);
		SubProduct sp = new SubProduct("sp1", dao.getProducts().get(0), System.currentTimeMillis(), new Position("2A"));
		dao.storeSubProduct(sp);
		
		assertEquals("List of subproducts differs from 1", 1, dao.getProducts().size());
		
		assertEquals("Subproducts differ", sp, dao.getSubProducts().get(0));
		
		assertEquals("Product differs", p1.getName(), dao.getProducts().get(0).getName());
	}
}
