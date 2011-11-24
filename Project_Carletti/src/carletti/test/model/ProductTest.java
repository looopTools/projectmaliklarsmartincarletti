/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Product;
import carletti.model.Treatment;

public class ProductTest
{

	Treatment treatment1;
	Treatment treatment2;
	Product p1;
	
	@Before
	public void setUp() throws Exception
	{
		treatment1 = new Treatment("Treatment1"); 
		
		p1 = new Product(01, "Produkt1", "Hej dette er en test",treatment1);
	}
	
	@Test
	public void Product()
	{
		Product p2 = new Product(02, "produkt2", "test", treatment1);
		assertEquals(02, p2.getId());
		assertEquals("produkt2", p2.getName());
		assertEquals("test", p2.getDescription());
		assertEquals(treatment1, p2.getTreatment());
	}

	@Test
	public void testGetTreatment()
	{
		assertEquals(treatment1, p1.getTreatment());
	}

	@Test
	public void testSetTreatment()
	{
		
		p1.setTreatment(treatment2);
		assertEquals(treatment2, p1.getTreatment());
	}

	@Test
	public void testGetId()
	{
		assertEquals(01, p1.getId());
	}

	@Test
	public void testGetName()
	{
		assertEquals("Produkt1", p1.getName());
	}

	@Test
	public void testSetName()
	{
		p1.setName("Produkt11");
		assertEquals("Produkt11", p1.getName());
	}

	@Test
	public void testGetDescription()
	{
		assertEquals("Hej dette er en test", p1.getDescription());
	}

	@Test
	public void testSetDescription()
	{
		p1.setDescription("Den nye");
		assertEquals("Den nye", p1.getDescription());
	}

}
