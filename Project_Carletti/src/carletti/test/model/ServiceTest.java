/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.model.State;

public class ServiceTest
{

	private Product product;
	private Treatment treatment;
	private SubProduct subproduct;
	private SubProduct subproduct2;
//	private Dao localDao = LocalDao.getInstance();
	private Dao dao = JpaDao.getInstance();
	
	private Position p1;
	private Position p2;
	
	private Service service;
	
	
	@Before
	public void setUp() throws Exception
	{
		service = Service.getInstance(dao);
		treatment = service.createTreatment("testTreatment");
		treatment.createSubTreatment("subTreatment1", 100, 200, 300);
		treatment.createSubTreatment("subTreatment2", 400, 500, 600);
		
		product = service.createProduct("product", "dette er en test", treatment );
		p1 = new Position("1");
		p2 = new Position("2");
		subproduct = service.createSubProduct("Sub", product, p1);
		subproduct2 = service.createSubProduct("Sub2", product, p2);
	}
	
	@After
	public void setAfter()
	{	
		for (SubProduct sp : dao.getSubProducts())
		{
			service.removeSubproduct(sp);
		}
		
		for (Product p : service.getProducts())
		{
			service.removeProduct(p);
		}
			
				
		for (Treatment t : dao.getTreatments())
		{
			service.removeTreatment(t);
		}
		
		

	}

	@Test
	public void testCreateSubProduct()
	{
		SubProduct subProduct = service.createSubProduct("test", product, p1);
		assertEquals("test", service.getSubproduct(subProduct).getName());
		assertEquals(product, service.getSubproduct(subProduct).getProduct());
		assertEquals(p1, service.getSubproduct(subProduct).getPosition());
	}

	@Test
	public void testRemoveSubproduct()
	{
		service.removeSubproduct(subproduct);
		
		assertEquals(null, service.getSubproduct(subproduct));
	}

	@Test
	public void testDiscardSubProduct()
	{
		service.discardSubProduct(subproduct);
		assertEquals(State.WASTE , service.getSubproduct(subproduct).getState());
	}

	@Test
	public void testGetSubproduct()
	{
		assertEquals("Sub2", service.getSubproduct(subproduct2).getName());
		assertEquals(product, service.getSubproduct(subproduct2).getProduct());
	}

	@Test
	public void testCreateTreatment()
	{
		Treatment treatment = service.createTreatment("treatment");
		Product p = service.createProduct("name", "description", treatment);
		
		assertEquals(treatment, service.getProduct(p).getTreatment());
	}

	@Test
	public void testCreateProduct()
	{
		Product product = service.createProduct("name", "description", treatment);
		assertEquals(product, service.getProduct(product));
	}

	@Test
	public void testRemoveProduct()
	{
		Treatment t = service.createTreatment("sadf"); 
		Product product = service.createProduct("asdfdsa", "dsafdsaf", t);
		service.removeProduct(product);
		assertEquals(null, service.getProduct(product));
	}

	@Test
	public void testGetProduct()
	{
		assertEquals(product, service.getProduct(product));
	}

	@Test
	public void testNextTreatnemt()
	{
		assertEquals("subTreatment1", service.getSubproduct(subproduct).getCurrentSubTreatment().getName());
		service.nextTreatnemt(subproduct);
		assertEquals("subTreatment2", service.getSubproduct(subproduct).getCurrentSubTreatment().getName());
	}

	@Test
	public void testSubProductDone()
	{
		service.changeState(subproduct, State.DONE);
		assertEquals(State.DONE, service.getSubproduct(subproduct).getState());
	}

	@Test
	public void testShowAllDoneProduct()
	{
		service.changeState(subproduct, State.DONE);
		assertEquals(1, service.showAllDoneProduct().size());
		service.changeState(subproduct2, State.DONE);
		assertEquals(2, service.showAllDoneProduct().size());
	}

	@Test
	public void testGetProducts()
	{
		assertEquals(1, service.getProducts().size());	
	}

	@Test
	public void testShowAllSubProduct()
	{
		assertEquals(2, service.showAllSubProduct().size());
	}

	@Test
	public void testGetDryingSubProduct()
	{
		assertEquals(2, service.getDryingSubProduct().size());
	}

	@Test
	public void testGetAllNotWastedSubProducts()
	{
		
		assertEquals(2, service.getAllDryingSubProducts().size());
	}

	@Test
	public void testGetAllInTreatment()
	{
		
		assertEquals(0, service.getAllInTreatment().size());
		service.changeState(subproduct2, State.TREATMENT);
		assertEquals(1, service.getAllInTreatment().size());
	}
	
}
