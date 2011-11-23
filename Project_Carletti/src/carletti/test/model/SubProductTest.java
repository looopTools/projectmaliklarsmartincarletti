package carletti.test.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * 
 * @author Malik Lund
 *
 */
public class SubProductTest {
	SubProduct subproduct;
	Treatment treatment;
	Product product;
	long timeAdded;

	@Before
	public void setUp() throws Exception {
		treatment = Service.createTreatment("Treatment");
		treatment.createSubTreatment("Subtreatment 1", 10, 20, 30);
		treatment.createSubTreatment("Subtreatment 2", 20, 30, 40);
		treatment.createSubTreatment("Subtreatment 3", 30, 40, 50);
		product = Service.createProduct("TestProduct", "Product to test", treatment);
		timeAdded = System.currentTimeMillis();
		subproduct = new SubProduct(1, "Name", product, timeAdded);
	}

	/**
	 * Test getters.
	 */
	@Test
	public void testSubProduct() {
		assertEquals("Time differs", timeAdded, subproduct.getTimeAdded());
		assertEquals("Id differs", 1, subproduct.getId());
		assertEquals("Name differs", "Name", subproduct.getName());
		assertEquals("State differs", State.DRYING, subproduct.getState());
		
		List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
		List<SubTreatment> expectedSubTreatments = treatment.getSubTreatments();
		assertEquals("Differing number of subtreatments", expectedSubTreatments.size(), actualSubTreatments.size());
		for (int i = 0; i < expectedSubTreatments.size(); i++){
			assertEquals("Differing SubTreatments", expectedSubTreatments.get(i), actualSubTreatments.get(i));
		}
		assertEquals("Current subtreatment differs", 0, subproduct.getCurrentSubTreatmentIndex());
		assertEquals("Product differs", product, subproduct.getProduct());
		assertEquals("Different subtreatments", treatment.getSubTreatments().get(0), subproduct.getCurrentSubTreatment());
	}
	
	/**
	 * Test setId
	 */
	@Test
	public void testSetId(){
		subproduct.setId(1337);
		assertEquals("Id not changed", 1337, subproduct.getId());
	}
	
	/**
	 * Test setName
	 */
	@Test
	public void testSetName(){
		subproduct.setName("NewName");
		assertEquals("Name not changed", "NewName", subproduct.getName());
	}
	
	/**
	 * Test setState
	 */
	@Test
	public void testSetState(){
		subproduct.setState(State.WASTE);
		assertEquals("State not changed", State.WASTE, subproduct.getState());
	}
	
	/**
	 * Test setSubtreatments
	 */
	@Test
	public void testSetSubtreatments(){
		subproduct.nextSubTreatment();
		subproduct.nextSubTreatment();
		
		Treatment newTreatment = Service.createTreatment("NewTreatment");
		newTreatment.createSubTreatment("NewSubTreatment", 50, 60, 70);
		subproduct.setSubtreatments(newTreatment.getSubTreatments());
		List<SubTreatment> expectedSubTreatments = newTreatment.getSubTreatments();
		List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
		for (int i = 0; i < expectedSubTreatments.size(); i++){
			assertEquals("Subproducts differs", expectedSubTreatments.get(i), actualSubTreatments.get(i));
		}
		
		subproduct.getCurrentSubTreatment();
	}
	
	/**
	 * Test addition of subTreatment
	 */
	@Test
	public void testAddSubTreatment(){
		SubTreatment st = new SubTreatment("AddedTreatment", 10, 12, 14);
		subproduct.addSubTreatment(st);
		assertEquals("List not enlarged", 4, subproduct.getSubtreatments().size());
		assertEquals("Last element not the recently added", st, subproduct.getSubtreatments().get(3));
	}
	
	/**
	 * Test product type change
	 */
	@Test
	public void testSetProduct(){
		subproduct.nextSubTreatment();
		subproduct.nextSubTreatment();
		subproduct.nextSubTreatment();
		
		Treatment newTreatment = Service.createTreatment("NewTreatment");
		newTreatment.createSubTreatment("T1", 1, 2, 3);
		newTreatment.createSubTreatment("T2", 2, 3, 4);
		Product newProduct = Service.createProduct("NewProduct", "Description of new product", newTreatment);
		subproduct.setProduct(newProduct);
		assertEquals("Product differs", newProduct, subproduct.getProduct());

		List<SubTreatment> expectedSubTreatments = newTreatment.getSubTreatments();
		List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
		for (int i = 0; i < expectedSubTreatments.size(); i++){
			assertEquals("Subproducts differs", expectedSubTreatments.get(i), actualSubTreatments.get(i));
		}
		
		subproduct.getCurrentSubTreatment();
	}
	
	/**
	 * Test nextSubTreatment()
	 */
	@Test
	public void testNextSubTreatment(){
		subproduct.nextSubTreatment();
		assertEquals("Iteration error", treatment.getSubTreatments().get(1), subproduct.getCurrentSubTreatment());
		
		subproduct.nextSubTreatment();
		subproduct.nextSubTreatment();
		subproduct.nextSubTreatment();
		assertEquals("Iterated too far", treatment.getSubTreatments().get(2), subproduct.getCurrentSubTreatment());
	}
}