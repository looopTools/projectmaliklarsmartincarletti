package carletti;

import carletti.gui.MainFrame;
import carletti.service.Service;
import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dao dao;
		if (args.length > 0 && args[0].trim().equals("-local")){
			dao = LocalDao.getInstance();
		} else {
			dao = JpaDao.getInstance();
		}
		Service s = Service.getInstance(dao);
		s.createSomeObjects();
		MainFrame nmf = new MainFrame();
	}
}
