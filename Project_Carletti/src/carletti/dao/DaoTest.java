package carletti.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Treatment;

public class DaoTest {
	Dao dao = JpaDao.getInstance();

	@Before
	public void setUp() throws Exception {
		dao = null;
		Thread.sleep(100);
		dao = JpaDao.getInstance();
	}

	@Test
	public void storeTreatmentTest() {
		Treatment t1 = new Treatment("TestTreatment");
		t1.createSubTreatment("st1", 1000, 2000, 3000);
		t1.createSubTreatment("st2", 1000, 2000, 3000);
		dao.storeTreatment(t1);
		assertEquals("Size differs from 1", 1, dao.getTreatments().size());
	}
	
	@Test
	public void testtest(){
		assertEquals("Size is not 0", 0, dao.getTreatments().size());
	}
}
