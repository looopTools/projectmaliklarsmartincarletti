package carletti;

import carletti.gui.MainFrame;
import carletti.gui.MainFrameTabbed;
import carletti.service.Service;
import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;

public class MainTabbedTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dao dao = LocalDao.getInstance();
		
		Service s = Service.getInstance(dao);
		s.createSomeObjects();
		MainFrameTabbed nmf = new MainFrameTabbed();
	}
}
