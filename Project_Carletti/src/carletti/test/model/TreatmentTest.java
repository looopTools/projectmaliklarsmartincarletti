/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Treatment;

public class TreatmentTest
{
	
	private Treatment t1;			

	@Before
	public void setUp() throws Exception
	{
		t1 = new Treatment("treatment1");
	}

	@Test
	public void testTreatment()
	{
		Treatment t2 = new Treatment("treatment2");
		assertEquals("treatment2", t2.getName());
	}

	@Test
	public void testCreateSubTreatment()
	{
		
		t1.createSubTreatment("name", 10, 20, 30);
		assertEquals("name", t1.getSubTreatments().get(0).getName());
		assertEquals(10, t1.getSubTreatments().get(0).getDryMin());
		assertEquals(20, t1.getSubTreatments().get(0).getDryPrime());
		assertEquals(30, t1.getSubTreatments().get(0).getDryMax());
	}

	@Test
	public void testGetName()
	{
		assertEquals("treatment1", t1.getName());
	}

	@Test
	public void testGetSubTreatments()
	{
		t1.createSubTreatment("name", 10, 20, 30);
		assertEquals("name", t1.getSubTreatments().get(0).getName());
		assertEquals(10, t1.getSubTreatments().get(0).getDryMin());
		assertEquals(20, t1.getSubTreatments().get(0).getDryPrime());
		assertEquals(30, t1.getSubTreatments().get(0).getDryMax());
	}

	@Test
	public void testSetName()
	{
		t1.setName("newName");
		assertEquals("newName", t1.getName());
	}

}
