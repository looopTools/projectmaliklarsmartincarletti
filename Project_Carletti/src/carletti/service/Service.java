package carletti.service;

import java.util.ArrayList;
import java.util.List;

import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;
import carletti.dao.Dao;

/**
 * 
 * 
 * @author Martin
 *
 */

public class Service
{
	
	private Dao dao;
	
	public SubProduct createSubProduct(int id, String name, Product product)
	{
		SubProduct sp = new SubProduct(id, name, product);
		dao.storeSubProduct(sp);
		return sp;
	}
	
	public void removeSubproduct(SubProduct subProduct)
	{
		dao.removeSubProduct(subProduct);
	}
	
	public void discardSubProduct(SubProduct subProduct)
	{
		State waste = State.WASTE;
		dao.changeStateOfSubProduct(subProduct, waste);
	}
	
	public SubProduct getSubproduct(SubProduct subProduct)
	{
		int i = dao.getSubProducts().indexOf(subProduct);
		return dao.getSubProducts().get(i);
	}
	
	public Treatment createTreatment(String name)
	{
		Treatment t = new Treatment(name);
		return t;
	}
	
	public Product createProduct(int id, String name, String description, Treatment treatment)
	{
		Product p = new Product(id, name, description, treatment);
		dao.storeProduct(p);
		return p;
	}
	
	public void removeProduct(Product product)
	{
		dao.removeProduct(product);
	}
	
	public Product getProduct(Product product)
	{
		int i = dao.getProducts().indexOf(product);
		return dao.getProducts().get(i);
	}
	
	public void nextTreatnemt(SubProduct subProduct)
	{
		subProduct.nextSubTreatment();
	}
	
	public void subProductDone(SubProduct subProduct)
	{
		State done = State.DONE;
		dao.changeStateOfSubProduct(subProduct, done);
	}
	
	public List<SubProduct> showAllDoneProduct()
	{
		State done = State.DONE;
		
		return dao.getSubProducts(done);
	}
	
	public List<SubProduct> showAllSubProduct()
	{
		return dao.getSubProducts();
	}
	
	public String getInfoAboutSubProduct(SubProduct subProduct)
	{
		int i = dao.getSubProducts().indexOf(subProduct);
		return dao.getSubProducts().get(i) + "";
	}
	
	/**
	 * 
	 */
	public void createSomeObjects()
	{
		Treatment t1 = createTreatment("Red chocolate MMs");
		t1.createSubTreatment("1st drying", 1000, 1250, 1500);
		t1.createSubTreatment("2nd drying", 500, 750, 1000);
		t1.createSubTreatment("3rd drying", 1250, 1300, 1500);
		Product p1 = createProduct(1, "Red Chocolate MMs", "Info about red chocolate MMs", t1);
		
		Treatment t2 = createTreatment("Liquorice");
		t2.createSubTreatment("1st drying", 1000, 1250, 1500);
		t2.createSubTreatment("2nd drying", 1500, 1750, 2000);
		Product p2 = createProduct(2, "Liquorice", "Liquorice with coloured sugar layer", t2);
		
		Treatment t3 = createTreatment("Coffeebean");
		t3.createSubTreatment("1st drying", 500, 600, 700);
		t3.createSubTreatment("2nd drying", 1200, 1300, 1400);
		t3.createSubTreatment("3rd drying", 300, 400, 500);
		Product p3 = createProduct(3, "Coffee Bean", "Coffee paste with a layer of chocolate", t3);
		
		SubProduct sp1 = createSubProduct(1, "Foobar", p1);
		SubProduct sp2 = createSubProduct(2, "Barbaz", p2);
		SubProduct sp3 = createSubProduct(3, "Bazfoo", p3);
	}
}
