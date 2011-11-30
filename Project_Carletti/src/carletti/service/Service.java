package carletti.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carletti.dao.Dao;
import carletti.model.Position;
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

	private Dao dao;
	private static Service serviceInstance;

	private Service(Dao dao)
	{
		this.dao = dao;
	}

	/**
	 * 
	 * @param dao
	 *            The dao to use. OBS: After initial call, dao doesn't change!
	 * @return The instance
	 * @author Malik Lund
	 */
	public static Service getInstance(Dao dao)
	{
		if (serviceInstance == null)
		{
			serviceInstance = new Service(dao);
		}
		return serviceInstance;
	}

	public SubProduct createSubProduct(String name, Product product,
			Position position)
	{
		SubProduct sp = new SubProduct(name, product,
				System.currentTimeMillis(), position); // TODO Make timestamp an
														// option to the user
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
		if (i >= 0)
		{
			return dao.getSubProducts().get(i);
		}
		else
		{
			return null;
		}
	}

	public Treatment createTreatment(String name)
	{
		Treatment t = new Treatment(name);
		return t;
	}

	public Product createProduct(String name, String description,
			Treatment treatment)
	{
		Product p = new Product(name, description, treatment);
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
		if (i >= 0)
		{
			return dao.getProducts().get(i);
		} 
		else
		{
			return null;
		}

	}

	public void nextTreatnemt(SubProduct subProduct)
	{
		if (subProduct.getCurrentSubTreatmentIndex() < (subProduct
				.getSubtreatments().size() - 1))
		{
			subProduct.setCurrentSubTreatmentIndex(subProduct
					.getCurrentSubTreatmentIndex() + 1);
			dao.changeStateOfSubProduct(subProduct, State.DRYING); // Martin
		} else if (subProduct.getCurrentSubTreatmentIndex() >= (subProduct
				.getSubtreatments().size() - 1))
		{
			dao.changeStateOfSubProduct(subProduct, State.DONE);
		}
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

	public List<Product> getProducts()
	{
		return dao.getProducts();
	}

	public List<SubProduct> showAllSubProduct()
	{
		List<SubProduct> list = dao.getSubProducts();

		Collections.sort(list);
		return list;
	}

	public String getInfoAboutSubProduct(SubProduct subProduct)
	{
		int i = dao.getSubProducts().indexOf(subProduct);
		return dao.getSubProducts().get(i) + "";
	}

	public List<SubProduct> getDryingSubProduct()
	{
		State drying = State.DRYING;
		return dao.getSubProducts(drying);
	}

	// --------- Malik-------------
	public void createSomeObjects(Dao dao)
	{
		Service s = Service.getInstance(dao);
		Treatment t1 = s.createTreatment("Red chocolate MMs");
		t1.createSubTreatment("1st drying", 1000 * 60 * 30, 1000 * 60 * 32,
				1000 * 60 * 35);
		t1.createSubTreatment("2nd drying", 10000, 20000, 30000);
		t1.createSubTreatment("3rd drying", 1250, 1300, 1500);
		Product p1 = s.createProduct("Red Chocolate MMs",
				"Info about red chocolate MMs", t1);

		Treatment t2 = s.createTreatment("Liquorice");
		t2.createSubTreatment("1st drying", 1000 * 60 * 15, 1000 * 60 * 20,
				1000 * 60 * 25);
		t2.createSubTreatment("2nd drying", 1500, 1750, 2000);
		Product p2 = s.createProduct("Liquorice",
				"Liquorice with coloured sugar layer", t2);

		Treatment t3 = s.createTreatment("Coffeebean");
		t3.createSubTreatment("1st drying", 1000 * 60 * 55, 1000 * 60 * 60,
				1000 * 60 * 70);
		t3.createSubTreatment("2nd drying", 1200, 1300, 1400);
		t3.createSubTreatment("3rd drying", 300, 400, 500);
		Product p3 = s.createProduct("Coffee Bean",
				"Coffee paste with a layer of chocolate", t3);

		SubProduct sp1 = s.createSubProduct("Foobar", p1, new Position("A1"));
		SubProduct sp2 = s.createSubProduct("Barbaz", p2, new Position("B4"));
		SubProduct sp3 = s.createSubProduct("Bazfoo", p3, new Position("C7"));
	}

	// ----------------------------

	// Add by Lars
	public List<SubProduct> getAllDryingSubProducts()
	{
		ArrayList<SubProduct> productsNotWasted = new ArrayList<SubProduct>();
		List<SubProduct> allSubProducts = dao.getSubProducts();
		for (int i = 0; i < allSubProducts.size(); i++)
		{
			if (allSubProducts.get(i).getState() == State.DRYING)
			{
				productsNotWasted.add(allSubProducts.get(i));
			}
		}

		Collections.sort(productsNotWasted); // Martin

		return productsNotWasted;
	}

	// ---------Martin---------------

	public List<SubProduct> getAllInTreatment()
	{
		ArrayList<SubProduct> subProductUnderTreatment = new ArrayList<SubProduct>();

		for (int i = 0; i < dao.getSubProducts().size(); i++)
		{
			if (dao.getSubProducts().get(i).getState() == State.TREATMENT)
			{
				subProductUnderTreatment.add(dao.getSubProducts().get(i));
			}
		}

		return subProductUnderTreatment;
	}

	// -----------------------------------

	public void changeState(SubProduct sp, State state)
	{
		dao.changeStateOfSubProduct(sp, state);
	}
}
