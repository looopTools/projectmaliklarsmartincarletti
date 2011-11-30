package carletti;

import carletti.gui.MainFrame;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Service s = Service.getInstance(JpaDao.getInstance());
		s.createSomeObjects(JpaDao.getInstance());
		MainFrame nmf = new MainFrame();
	}
}
