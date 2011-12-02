package carletti.dao;

import java.util.ArrayList;
import java.util.List;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

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
public class LocalDao implements Dao {
	private static LocalDao dao = null;
	private ArrayList<Treatment> treatments;
	private ArrayList<Product> products;
	private ArrayList<SubProduct> subproducts;
	private ArrayList<Position> positions;
	private int subProductnextID = 1;
	private int productNextID = 1;
	/**
	 * Simply creates a Dao-object and initializes all
	 * lists.
	 */
	private LocalDao(){
		treatments = new ArrayList<Treatment>();
		products = new ArrayList<Product>();
		subproducts = new ArrayList<SubProduct>();		
		positions = new ArrayList<Position>();
	}
	
	/**
	 * Creates a new instance of the Dao class on the first call
	 * and merely returns this for any subsequent calls. 
	 * @return An instance of Dao.
	 */
	public static Dao getInstance(){
		if (dao == null){
			dao = new LocalDao();
		}
		return dao;
	}
	
	/**
	 * Returns a list of SubProducts in the given state.
	 * @param state The state of the returned SubProducts. 
	 * @return A list of SubProducts in a given state. If state is null
	 *         all SubProduct objects are returned.
	 */
	public List<SubProduct> getSubProducts(State state){
		ArrayList<SubProduct> selectedSubproducts = new ArrayList<SubProduct>();
		for (SubProduct sp: subproducts){
			if (sp.getState().equals(state) || state == null){
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
		Treatment t = p.getTreatment();
		if (!treatments.contains(t)){
			storeTreatment(t);
		}
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
	
	public int getSubProducNextID(){
		return subProductnextID;
	}
	
	public void countSubProducID(){
		subProductnextID++;
	}
	
	public int getProducNextID(){
		return productNextID;
	}
	
	public void countProducID(){
		productNextID++;
	}

	@Override
	public void storeTreatment(Treatment t) {
		treatments.add(t);
	}

	@Override
	public void removeTreatment(Treatment t) {
		treatments.remove(t);
	}

	@Override
	public List<Treatment> getTreatments() {
		return new ArrayList<Treatment>(treatments);
	}
	
	//-----Lars
	@Override
	public List<Position> getPositions() {
		return new ArrayList<Position>(positions);
	}

	
	@Override
	public void storePosition(Position p) {
		positions.add(p);
		
	}

	@Override
	public void removePosition(Position p) {
		positions.remove(p);
		
	}
	//-----
	
	
}
