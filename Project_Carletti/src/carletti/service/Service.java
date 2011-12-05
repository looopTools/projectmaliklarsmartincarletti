package carletti.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carletti.dao.Dao;
import carletti.dao.LocalDao;
import carletti.model.LongToStringParser;
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

	/**
	 * Simple getInstance method that defaults to a local, impersistent storage
	 * in memory. If a call has been made to getInstance(Dao) an instance with
	 * the previously selected Dao is returned.
	 * 
	 * @return
	 */
	public static Service getInstance()
	{
		if (serviceInstance == null)
		{
			serviceInstance = new Service(LocalDao.getInstance());
		}
		return serviceInstance;
	}

	/**
	 * 
	 * @param name
	 *            The name of the sup-product.
	 * @param product
	 *            The product of the sub-product.
	 * @param position
	 *            The position of the sub-product.
	 * @return Sub-product.
	 */
	public SubProduct createSubProduct(String name, Product product,
			Position position)
	{
		SubProduct sp = new SubProduct(name, product,
				System.currentTimeMillis(), position); // TODO Make timestamp an
														// option to the user
		dao.storeSubProduct(sp);

		return sp;
	}

	/**
	 * Remove sub-product
	 * 
	 * @param subProduct
	 *            The sub-product that is to be removed.
	 */
	public void removeSubproduct(SubProduct subProduct)
	{
		dao.removeSubProduct(subProduct);
	}

	/**
	 * Waste sub-product
	 * 
	 * @param subProduct
	 *            The sub-product that is to be wasted
	 */
	public void discardSubProduct(SubProduct subProduct)
	{
		State waste = State.WASTE;
		dao.changeStateOfSubProduct(subProduct, waste);
	}

	/**
	 * Get sub-product
	 * 
	 * @param subProduct
	 *            The sub-product to be returned.
	 * @return Sub-product.
	 */
	public SubProduct getSubproduct(SubProduct subProduct)
	{

		int i = dao.getSubProducts().indexOf(subProduct);
		if (i >= 0)
		{
			return dao.getSubProducts().get(i);
		} else
		{
			return null;
		}
	}

	/**
	 * 
	 * @author Malik Lund
	 * @return
	 */
	public List<Treatment> getTreatments()
	{
		return dao.getTreatments();
	}

	/**
	 * Create treatment.
	 * 
	 * @param name
	 *            The name of the treatment
	 * @return Treatment
	 */
	public Treatment createTreatment(String name)
	{
		Treatment t = new Treatment(name);
		return t;
	}

	/**
	 * remove treatment
	 * 
	 * @param treatment
	 *            The treatment to be removed.
	 */
	public void removeTreatment(Treatment treatment)
	{
		dao.removeTreatment(treatment);
	}

	/**
	 * Create new Product.
	 * 
	 * @param name
	 *            The name of the product.
	 * @param description
	 *            A description of the product.
	 * @param treatment
	 *            The treatment for the product.
	 * @return Product.
	 */
	public Product createProduct(String name, String description,
			Treatment treatment)
	{
		Product p = new Product(name, description, treatment);
		dao.storeProduct(p);
		return p;
	}

	/**
	 * Remove product
	 * 
	 * @param product
	 *            The product to be removed.
	 */
	public void removeProduct(Product product)
	{
		dao.removeProduct(product);
	}

	/**
	 * Get product.
	 * @param product The product to be returned
	 * @return Product.
	 */
	public Product getProduct(Product product)
	{
		int i = dao.getProducts().indexOf(product);
		if (i >= 0)
		{
			return dao.getProducts().get(i);
		} else
		{
			return null;
		}

	}
	
	/**
	 * Next treatment.
	 * @param subProduct The sub-product to be sent to the next treatment
	 */
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

	/**
	 * Set product done.
	 * @param subProduct The subproduct of which the state is to change to done.
	 */
	public void subProductDone(SubProduct subProduct)
	{
		State done = State.DONE;
		dao.changeStateOfSubProduct(subProduct, done);
	}

	/**
	 * Gets list of all done products.
	 * @return
	 */
//	public List<SubProduct> showAllDoneProduct()
//	{
//		State done = State.DONE;
//
//		return dao.getSubProducts(done);
//	}
//
	public List<Product> getProducts()
	{
		return dao.getProducts();
	}
//
//	public List<SubProduct> showAllSubProduct()
//	{
//		List<SubProduct> list = dao.getSubProducts();
//
//		Collections.sort(list);
//		return list;
//	}
//
//	public String getInfoAboutSubProduct(SubProduct subProduct)
//	{
//		int i = dao.getSubProducts().indexOf(subProduct);
//		return dao.getSubProducts().get(i) + "";
//	}
//
//	public List<SubProduct> getDryingSubProduct()
//	{
//		State drying = State.DRYING;
//		return dao.getSubProducts(drying);
//	}

	// --------- Malik-------------
	public void createSomeObjects()
	{
		Service s = Service.getInstance();

		s.createPosition("A1");
		s.createPosition("A2");
		s.createPosition("A3");
		s.createPosition("B1");
		s.createPosition("B2");
		s.createPosition("B3");
		s.createPosition("C1");
		s.createPosition("C2");
		s.createPosition("C3");

		Treatment t1 = s.createTreatment("Red chocolate MMs");
		t1.createSubTreatment("1st drying", generateTime(2), generateTime(30),
				generateTime(0, 1));
		t1.createSubTreatment("2nd drying", generateTime(0, 35),
				generateTime(0, 40), generateTime(0, 50));
		t1.createSubTreatment("3rd drying", generateTime(0, 35),
				generateTime(0, 40), generateTime(0, 50));
		Product p1 = s.createProduct("Red Chocolate MMs",
				"Info about red chocolate MMs", t1);

		Treatment t2 = s.createTreatment("Liquorice");
		t2.createSubTreatment("1st drying", generateTime(0, 15),
				generateTime(0, 20), generateTime(0, 25));
		t2.createSubTreatment("2nd drying", generateTime(0, 35),
				generateTime(0, 40), generateTime(0, 50));
		Product p2 = s.createProduct("Liquorice",
				"Liquorice with coloured sugar layer", t2);

		Treatment t3 = s.createTreatment("Coffeebean");
		t3.createSubTreatment("1st drying", generateTime(0, 55),
				generateTime(0, 60), generateTime(0, 10, 1));
		t3.createSubTreatment("2nd drying", generateTime(0, 35),
				generateTime(0, 40), generateTime(0, 50));
		t3.createSubTreatment("3rd drying", generateTime(0, 35),
				generateTime(0, 40), generateTime(0, 50));
		Product p3 = s.createProduct("Coffee Bean",
				"Coffee paste with a layer of chocolate", t3);

		List<Position> positions = s.getPositions();

		SubProduct sp1 = s.createSubProduct("Foobar", p1, positions.get(0));
		SubProduct sp2 = s.createSubProduct("Barbaz", p2, positions.get(4));
		SubProduct sp3 = s.createSubProduct("Bazfoo", p3, positions.get(8));
	}

	/**
	 * Takes a variable number of arguments and generates a time in
	 * milliseconds.
	 * 
	 * The first value represents seconds, the second minutes, the third hours
	 * and the fourth hours. Any arguments after that are ignored.
	 * 
	 * @author Malik Lund
	 * @param times
	 * @return
	 */
	private long generateTime(int... times)
	{
		long time = 0;
		long[] constants =
		{ 1000, 1000 * 60, 1000 * 60 * 60, 1000 * 60 * 60 * 24 };
		int roof = Math.min(times.length, 4);
		for (int i = 0; i < roof; i++)
		{
			time += times[i] * constants[i];
		}
		return time;
	}

	// ----------------------------

	
	//--------Martin---------------
	/**
	 * Get a list of all sub-product with the given state. 
	 * @param state The state for the product to be returned.
	 * @return list of products.
	 */
	public List<SubProduct> getSubProducts(State state)
	{
		ArrayList<SubProduct> subProduct = new ArrayList<SubProduct>();
		for (int i = 0; i < dao.getSubProducts().size(); i++)
		{
			if (dao.getSubProducts().get(i).getState() == state)
			{
				subProduct.add(dao.getSubProducts().get(i));
			}
		}
		Collections.sort(subProduct);
		return subProduct;
	}
	//--------------------------------

	
	// -----Lars
	public List<Position> getPositions()
	{
		return dao.getPositions();
	}

	public void createPosition(String posID)
	{
		dao.storePosition(new Position(posID));

	}

	// -----

	/**
	 * 
	 * @param sp
	 *            The product of which the state id to change.
	 * @param state
	 *            The new state of the product.
	 */
	public void changeState(SubProduct sp, State state)
	{
		dao.changeStateOfSubProduct(sp, state);
	}
}
