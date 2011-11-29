package carletti;

import java.util.List;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.service.jpa.ServiceJpa;

public class JpaTEST {
	
	public static void main(String[] args){
		Treatment t1 = Service.createTreatment("Martins MMs");
		t1.createSubTreatment("1st drying", 1000*60*30, 1000*60*32, 1000*60*35);
		t1.createSubTreatment("2nd drying", 500, 750, 1000);
		t1.createSubTreatment("3rd drying", 1250, 1300, 1500);
		Product p1 = Service.createProduct("Red Chocolate MMs", "Info about red chocolate MMs", t1);
		SubProduct sp1 = Service.createSubProduct("Foobar", p1);
		Dao dao = JpaDao.getInstance();
		
		Treatment t2 = Service.createTreatment("Maliks Slik");
		t2.createSubTreatment("1st", 1000, 2000, 3000);
		t2.createSubTreatment("2nd", 3000, 4000, 5000);
		Product p2 = Service.createProduct("FooBarCandy", "Some FooBarCandy from Baz company", t2);
		SubProduct sp2 = Service.createSubProduct("Malik", p2);
		dao.storeSubProduct(sp2);
//		dao.storeProduct(p2);
//		JpaDao.getInstance().storeProduct(p1);
		ServiceJpa.getInstance().storeSubProduct(sp1);
		List<SubProduct> subProducts = JpaDao.getInstance().getSubProducts(State.DRYING);
		System.out.println(subProducts);
//		dao.changeStateOfSubProduct(subProducts.get(0), State.WASTE);
		System.out.println(JpaDao.getInstance().getSubProducts(State.WASTE));
	}
}
