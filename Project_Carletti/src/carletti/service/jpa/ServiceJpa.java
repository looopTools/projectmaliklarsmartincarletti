/**
 * @author Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * @programmer Lars Nielsen
 * @class ServiceJpa
 */

package carletti.service.jpa;

import carletti.dao.jpa.JpaDao;
import carletti.model.SubProduct;

public class ServiceJpa {
		
	private static ServiceJpa uniqueInstance;
	
	
	private ServiceJpa(){
		
	}
	
	public static ServiceJpa getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new ServiceJpa();
		}
		
		return uniqueInstance;
	}
	
	public void storeSubProduct(SubProduct sp){
		JpaDao.getInstance().storeSubProduct(sp);
	}
	
	
}
