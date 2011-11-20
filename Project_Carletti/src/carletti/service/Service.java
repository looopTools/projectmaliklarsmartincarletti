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
	
	public void createSubProduct(int id, String name, State state, Product product)
	{
		SubProduct sp = new SubProduct(id, name, state, product);
		dao.storeSubProduct(sp);
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
	
	public void createProduct(int id, String name, String description, Treatment treatment)
	{
//		Constructor missing in product!
		Product p = new Product(id, name, description, treatment);
		dao.storeProduct(p);
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
}
