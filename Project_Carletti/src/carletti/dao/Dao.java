package carletti.dao;

import java.util.List;

import carletti.model.Position;
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
	public void storePosition(Position p);
	public void removePosition(Position p);
	public List<Treatment> getTreatments();
	public List<Product> getProducts();
	public List<SubProduct> getSubProducts();
	public List<Position> getPositions();
}
