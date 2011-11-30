package carletti.test.model;

import static org.junit.Assert.*;

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
	private Dao localDao = LocalDao.getInstance();
//	private Dao dao = JpaDao.getInstance();
	
	private Position p1;
	private Position p2;
	
	private Service service;
	
	
	@Before
	public void setUp() throws Exception
	{
		
		service = Service.getInstance(localDao);
		treatment = service.createTreatment("testTreatment");
		treatment.createSubTreatment("subTreatment1", 100, 200, 300);
		treatment.createSubTreatment("subTreatment2", 400, 500, 600);
		
		product = service.createProduct("product", "dette er en test", treatment );
		p1 = new Position("1");
		p2 = new Position("2");
		subproduct = service.createSubProduct("Sub", product, p1);
		subproduct2 = service.createSubProduct("Sub2", product, p2);
		
	}

	@Test
	public void testCreateSubProduct()
	{
		SubProduct d = service.createSubProduct("test", product, p1);
		assertEquals("test", service.getSubproduct(d).getName());
		assertEquals(product, service.getSubproduct(d).getProduct());
		
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
		
		State s = State.WASTE;
		
		assertEquals(s , service.getSubproduct(subproduct).getState());
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
		Treatment t = service.createTreatment("treatment");
		Product p = service.createProduct("name", "description", t);
		
		assertEquals(t, service.getProduct(p).getTreatment());
	}

	@Test
	public void testCreateProduct()
	{
		Product p = service.createProduct("name", "description", treatment);
		assertEquals(p, service.getProduct(p));
	}

	@Test
	public void testRemoveProduct()
	{
		service.createProduct("afasf", "adsfasf", treatment);
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
		assertEquals(1, service.showAllDoneProduct().size());
		service.changeState(subproduct2, State.DONE);
		assertEquals(2, service.showAllDoneProduct().size());
	}

	@Test
	public void testGetProducts()
	{
		
		assertEquals(14, service.getProducts().size());
		
	}

	@Test
	public void testShowAllSubProduct()
	{
		assertEquals(26, service.showAllSubProduct().size());
	}

	@Test
	public void testGetDryingSubProduct()
	{
		assertEquals(25, service.getDryingSubProduct().size());
	}

	@Test
	public void testGetAllNotWastedSubProducts()
	{
		
		assertEquals(27, service.getAllDryingSubProducts().size());
	}

	@Test
	public void testGetAllInTreatment()
	{
		
		assertEquals(0, service.getAllInTreatment().size());
		service.changeState(subproduct2, State.TREATMENT);
		assertEquals(1, service.getAllInTreatment().size());
	}
	
}
