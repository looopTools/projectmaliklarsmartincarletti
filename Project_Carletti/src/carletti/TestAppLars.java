package carletti;

import carletti.gui.NewMainFrame;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class TestAppLars {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Service s = Service.getInstance(JpaDao.getInstance());
//		s.createSomeObjects(JpaDao.getInstance());
		NewMainFrame nmf = new NewMainFrame();
	}

}
