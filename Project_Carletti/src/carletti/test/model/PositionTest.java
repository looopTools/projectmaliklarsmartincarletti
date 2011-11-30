/**
 * @author Lars Nielsen, Mailk Lasse Lund, Martin R¿n Bundgaard
 * @Class PositionTest
 * @programmer Lars Nielsen
 */
package carletti.test.model;

import org.junit.Test;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

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
		Product pd = new Product("name", "description", tm);
		SubProduct sp = new SubProduct("Judas", pd, 10000, new Position("1M"));
		Position p = new Position("1A");
		p.putSubProductOnPosition(sp);
	}

	@Test
	public void testRemoveSubProductFromPosition() {
//		fail("Not yet implemented");
		Treatment tm = new Treatment("Kain");
		Product pd = new Product("name", "description", tm);
		SubProduct sp = new SubProduct("Judas", pd, 10000, new Position("2M"));
		Position p = new Position("1A");
		p.putSubProductOnPosition(sp);
		p.removeSubProductFromPosition();
	}

}
