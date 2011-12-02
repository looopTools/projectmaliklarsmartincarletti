package carletti.dao;

import java.util.List;

import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * @author Malik Lund
 *
 */
public interface Dao {

	public List<SubProduct> getSubProducts(State state);
	public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state);
	public void storeTreatment(Treatment t);
	public void removeTreatment(Treatment t);
	public void storeProduct(Product p);
	public void removeProduct(Product p);
	public void storeSubProduct(SubProduct sp);
	public void removeSubProduct(SubProduct sp);
	public List<Treatment> getTreatments();
	public List<Product> getProducts();
	public List<SubProduct> getSubProducts();
}
