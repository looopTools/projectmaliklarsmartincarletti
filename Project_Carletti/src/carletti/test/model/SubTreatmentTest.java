/**
 * @author Martin
 */


package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.SubTreatment;

public class SubTreatmentTest
{
	
	private SubTreatment st;

	@Before
	public void setUp() throws Exception
	{
		st = new SubTreatment("subtreatment1", 10, 20, 30);
	}

	@Test
	public void testSubTreatment()
	{
		SubTreatment s = new SubTreatment("Proeve", 40, 50, 60);
		assertEquals("Proeve", s.getName());
		assertEquals(40, s.getDryMin());
		assertEquals(50, s.getDryPrime());
		assertEquals(60, s.getDryMax());
	}

	@Test
	public void testGetDryMin()
	{
		assertEquals(10, st.getDryMin());
		
	}

	@Test
	public void testSetDryMin()
	{
		st.setDryMin(15);
		assertEquals(15, st.getDryMin());
	}

	@Test
	public void testGetDryPrime()
	{
		assertEquals(20, st.getDryPrime());
	}

	@Test
	public void testSetDryPrime()
	{
		st.setDryPrime(25);
		assertEquals(25, st.getDryPrime());
	}

	@Test
	public void testGetDryMax()
	{
		assertEquals(30, st.getDryMax());
	}

	@Test
	public void testSetDryMax()
	{
		st.setDryMax(65);
		assertEquals(65, st.getDryMax());
	}

	@Test
	public void testGetName()
	{
		assertEquals("subtreatment1", st.getName());
	}

	@Test
	public void testSetName()
	{
		st.setName("newName");
		assertEquals("newName", st.getName());
	}

	@Test
	public void testToString()
	{
		String s = "\n" + "subtreatment1" + "\n" + "min= " + "10" + "\nprime= " + "20" + "\nmax= " + "30";
		assertEquals(s, st.toString());
	}
}
