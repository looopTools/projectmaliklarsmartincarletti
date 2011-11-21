package carletti.service;

import java.util.ArrayList;
import java.util.List;

import carletti.dao.Dao;
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
	
	private static Dao dao = Dao.getInstance();
	
	public static SubProduct createSubProduct(int id, String name, Product product)
	{
		SubProduct sp = new SubProduct(id, name, product);
		dao.storeSubProduct(sp);
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
		return dao.getSubProducts().get(i);
	}
	
	public static Treatment createTreatment(String name)
	{
		Treatment t = new Treatment(name);
		return t;
	}
	
	public static Product createProduct(int id, String name, String description, Treatment treatment)
	{
		Product p = new Product(id, name, description, treatment);
		dao.storeProduct(p);
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
		return dao.getSubProducts();
	}
	
	public static String getInfoAboutSubProduct(SubProduct subProduct)
	{
		int i = dao.getSubProducts().indexOf(subProduct);
		return dao.getSubProducts().get(i) + "";
	}
	
	public static int getNextID(){
		return dao.getNextID();
	}
	
	public static void countID(){
		dao.countID();
	}
	
	/**
	 * 
	 */
	public static void createSomeObjects()
	{
		Treatment t1 = Service.createTreatment("Red chocolate MMs");
		t1.createSubTreatment("1st drying", 1000, 1250, 1500);
		t1.createSubTreatment("2nd drying", 500, 750, 1000);
		t1.createSubTreatment("3rd drying", 1250, 1300, 1500);
		Product p1 = Service.createProduct(1, "Red Chocolate MMs", "Info about red chocolate MMs", t1);
		
		Treatment t2 = Service.createTreatment("Liquorice");
		t2.createSubTreatment("1st drying", 1000, 1250, 1500);
		t2.createSubTreatment("2nd drying", 1500, 1750, 2000);
		Product p2 = Service.createProduct(2, "Liquorice", "Liquorice with coloured sugar layer", t2);
		
		Treatment t3 = Service.createTreatment("Coffeebean");
		t3.createSubTreatment("1st drying", 500, 600, 700);
		t3.createSubTreatment("2nd drying", 1200, 1300, 1400);
		t3.createSubTreatment("3rd drying", 300, 400, 500);
		Product p3 = Service.createProduct(3, "Coffee Bean", "Coffee paste with a layer of chocolate", t3);
		
		SubProduct sp1 = Service.createSubProduct(1, "Foobar", p1);
		SubProduct sp2 = Service.createSubProduct(2, "Barbaz", p2);
		SubProduct sp3 = Service.createSubProduct(3, "Bazfoo", p3);
	}
}
