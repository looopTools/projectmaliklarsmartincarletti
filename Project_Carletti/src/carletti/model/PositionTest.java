package carletti.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {

	@Test
	public void testPosition() {
		//fail("Not yet implemented");
		Position p = new Position("1A");
		
	}

	@Test
	public void testGetPosID() {
		//fail("Not yet implemented");
		Position p = new Position("1A");
		p.getPosID();
	}

	@Test
	public void testSetPosID() {
//		fail("Not yet implemented");
		Position p = new Position("1A");
		p.setPosID("2A");
	}

	@Test
	public void testPutSubProductOnPosition() {
//		fail("Not yet implemented");
		Treatment tm = new Treatment("Kain");
		Product pd = new Product(1, "name", "description", tm);
		SubProduct sp = new SubProduct(10, "Judas", pd, 10000);
		Position p = new Position("1A");
		p.putSubProductOnPosition(sp);
	}

	@Test
	public void testRemoveSubProductFromPosition() {
//		fail("Not yet implemented");
		Treatment tm = new Treatment("Kain");
		Product pd = new Product(1, "name", "description", tm);
		SubProduct sp = new SubProduct(10, "Judas", pd, 10000);
		Position p = new Position("1A");
		p.putSubProductOnPosition(sp);
		p.removeSubProductFromPosition();
	}

}
