package carletti.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carletti.dao.Dao;
import carletti.dao.LocalDao;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * 
 * @author Martin
 *
 */

public class Service
{
	
	private static Dao dao = LocalDao.getInstance();
	
	public static SubProduct createSubProduct(String name, Product product)
	{
		int id = dao.getSubProducNextID();
		SubProduct sp = new SubProduct(id, name, product, System.currentTimeMillis()); // TODO Make timestamp an option to the user
		dao.storeSubProduct(sp);
		dao.countSubProducID();
		
		return sp;
	}
	
	public static void removeSubproduct(SubProduct subProduct)
	{
		dao.removeSubProduct(subProduct);
	}
	
	public static void discardSubProduct(SubProduct subProduct)
	{
		State waste = State.WASTE;
		dao.changeStateOfSubProduct(subProduct, waste);
	}
	
	public static SubProduct getSubproduct(SubProduct subProduct)
	{
		
		int i = dao.getSubProducts().indexOf(subProduct);
		if(i >0)
			
		return dao.getSubProducts().get(i);
		
		else
		{
			return null;
		}
	}
	
	public static Treatment createTreatment(String name)
	{
		Treatment t = new Treatment(name);
		return t;
	}
	
	public static Product createProduct(String name, String description, Treatment treatment)
	{
		int id = dao.getProducNextID();
		Product p = new Product(id, name, description, treatment);
		dao.storeProduct(p);
		dao.countProducID();
		return p;
	}
	
	public static void removeProduct(Product product)
	{
		dao.removeProduct(product);
	}
	
	public static Product getProduct(Product product)
	{
		int i = dao.getProducts().indexOf(product);
		return dao.getProducts().get(i);
	}
	
	public static void nextTreatnemt(SubProduct subProduct)
	{
		subProduct.nextSubTreatment();
	}
	
	public static void subProductDone(SubProduct subProduct)
	{
		State done = State.DONE;
		dao.changeStateOfSubProduct(subProduct, done);
	}
	
	public static List<SubProduct> showAllDoneProduct()
	{
		State done = State.DONE;
		
		return dao.getSubProducts(done);
	}
	
	public static List<Product> getProducts(){
		return dao.getProducts();
	}
	
	public static List<SubProduct> showAllSubProduct()
	{
		List<SubProduct> list = LocalDao.getInstance().getSubProducts();
		
		Collections.sort(list);
		return list;
	}
	
	public static String getInfoAboutSubProduct(SubProduct subProduct)
	{
		int i = dao.getSubProducts().indexOf(subProduct);
		return dao.getSubProducts().get(i) + "";
	}
	
	public static List<SubProduct> getDryingSubProduct()
	{
		State drying = State.DRYING;
		return dao.getSubProducts(drying);
	}
	
	//--------- Malik-------------
	public static void createSomeObjects()
	{
		Treatment t1 = Service.createTreatment("Red chocolate MMs");
		t1.createSubTreatment("1st drying", 1000*60*30, 1000*60*32, 1000*60*35);
		t1.createSubTreatment("2nd drying", 500, 750, 1000);
		t1.createSubTreatment("3rd drying", 1250, 1300, 1500);
		Product p1 = Service.createProduct("Red Chocolate MMs", "Info about red chocolate MMs", t1);
		
		Treatment t2 = Service.createTreatment("Liquorice");
		t2.createSubTreatment("1st drying", 1000*60*15, 1000*60*20, 1000*60*25);
		t2.createSubTreatment("2nd drying", 1500, 1750, 2000);
		Product p2 = Service.createProduct("Liquorice", "Liquorice with coloured sugar layer", t2);
		
		Treatment t3 = Service.createTreatment("Coffeebean");
		t3.createSubTreatment("1st drying", 1000*60*55, 1000*60*60, 1000*60*70);
		t3.createSubTreatment("2nd drying", 1200, 1300, 1400);
		t3.createSubTreatment("3rd drying", 300, 400, 500);
		Product p3 = Service.createProduct("Coffee Bean", "Coffee paste with a layer of chocolate", t3);
		
		SubProduct sp1 = Service.createSubProduct("Foobar", p1);
		SubProduct sp2 = Service.createSubProduct("Barbaz", p2);
		SubProduct sp3 = Service.createSubProduct("Bazfoo", p3);
	}
	//----------------------------
	
	//Add by Lars
	public static List<SubProduct> getAllNotWastedSubProducts(){
		ArrayList<SubProduct> productsNotWasted = new ArrayList<SubProduct>();
		
		for(int i = 0; i < LocalDao.getInstance().getSubProducts().size(); i++){
			if(LocalDao.getInstance().getSubProducts().get(i).getState() == State.DRYING){
				productsNotWasted.add(LocalDao.getInstance().getSubProducts().get(i));
			}
		}
		
		Collections.sort(productsNotWasted); // Martin
		
		return productsNotWasted;
	}
	
//	---------Martin---------------
	
	public static List<SubProduct> getAllInTreatment()
	{
		ArrayList<SubProduct> subProductUnderTreatment = new ArrayList<SubProduct>();
		
		for (int i = 0 ; i < LocalDao.getInstance().getSubProducts().size() ; i ++)
		{
			if (LocalDao.getInstance().getSubProducts().get(i).getState() == State.TREATMENT)
			{
				subProductUnderTreatment.add(LocalDao.getInstance().getSubProducts().get(i));
			}
		}
		
		return subProductUnderTreatment;
	}
//	-----------------------------------
}
