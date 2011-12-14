package carletti.service;

import java.util.List;

import carletti.dao.Dao;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

public class Service
{

	public Service()
	{

	}

	private static Service serviceInstance;

	public static Service getInstance()
	{
		if (serviceInstance == null)
		{
			serviceInstance = new Service();
		}
		return serviceInstance;
	}

	public void DryingTime(int id)
	{
		Dao.drying(id);

	}

	public Product createProduct(int id, String name, String description,
			Treatment treatment)
	{
		Product p = null;
		try
		{
			p = new Product(id, name, description, treatment);
		} 
		catch (NullPointerException e)
		{
			System.out.println("Du har ikke skrivet en description");
		}

		return p;
	}

	public SubProduct createSubProduct(String name, Product product,
			Position position)
	{
		SubProduct sp = null;
		try
		{
			sp = new SubProduct(name, product, System.currentTimeMillis(),
					position); // TODO Make timestamp an
								// option to the user
			Dao.storeSubproduct(sp);
		} catch (NullPointerException e)
		{
			System.out.println("Et objekt mangler at blive valgt");
		}
		return sp;
	}
	
	

}