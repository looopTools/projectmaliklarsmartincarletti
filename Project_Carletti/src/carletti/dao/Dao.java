package carletti.dao;

import java.util.ArrayList;
import java.util.List;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Product;

/**
 * This class maintains connection, and any operations, with the database.
 * 
 * It is implemented as a Singleton-object to ensure there is only one
 * object interacting with the database.
 * To get an instance call the static method Dao.getInstance().
 * 
 * TODO Change implementation to use a database rather than lists.
 * 
 * @author Malik Lund
 *
 */
public class Dao {
	private static Dao dao = null;
	private ArrayList<Product> products;
	private ArrayList<SubProduct> subproducts;
	
	/**
	 * Simply creates a Dao-object and initializes all
	 * lists.
	 */
	private Dao(){
		products = new ArrayList<Product>();
		subproducts = new ArrayList<SubProduct>();
	}
	
	/**
	 * Creates a new instance of the Dao class on the first call
	 * and merely returns this for any subsequent calls. 
	 * @return An instance of Dao.
	 */
	public static Dao getInstance(){
		if (dao == null){
			dao = new Dao();
		}
		return dao;
	}
	
	/**
	 * Returns a list of SubProducts in the given state.
	 * @param state The state of the returned SubProducts
	 * @return A list of SubProducts in a given state.
	 */
	public List<SubProduct> getSubProducts(State state){
		ArrayList<SubProduct> selectedSubproducts = new ArrayList<SubProduct>();
		for (SubProduct sp: subproducts){
			if (sp.getState().equals(state)){
				selectedSubproducts.add(sp);
			}
		}
		return selectedSubproducts;
	}
	
	/**
	 * Changes the state of a SubProduct-object and saves it in the
	 * database.
	 * @param subProduct The object to change the state of.
	 * @param state The state to change to.
	 * @return The object with its new state.
	 */
	public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state){
		subProduct.setState(state);
		return subProduct;
	}
	
	/**
	 * Store a Product object in the database.
	 * @param p The Product object to store.
	 */
	public void storeProduct(Product p){
		products.add(p);
	}
	
	/**
	 * Delete a product type from the system.
	 * @param p The Product object to delete.
	 */
	public void removeProduct(Product p){
		products.remove(p);
	}

	/**
	 * Store a SubProduct object in the database.
	 * @param sp The SubProduct object to store.
	 */
	public void storeSubProduct(SubProduct sp){
		subproducts.add(sp);
	}
	
	/**
	 * Delete a sub-product from the system.
	 * @param sp The SubProduct object to delete.
	 */
	public void removeSubProduct(SubProduct sp){
		subproducts.remove(sp);
	}
	
	/**
	 * Get a list of all product types.
	 * @return A list of Product objects.
	 */
	public List<Product> getProducts(){
		return new ArrayList<Product>(products);
	}
	
	/**
	 * Get a list of all sub-product types.
	 * @return A list of SubProduct objects.
	 */
	public List<SubProduct> getSubProducts(){
		return new ArrayList<SubProduct>(subproducts);
	}
}
