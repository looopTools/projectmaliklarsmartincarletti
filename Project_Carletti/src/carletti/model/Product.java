package carletti.model;


/**
 * Product represents encapsulates the name,
 * description and treatment of each sub-product.
 * 
 * @author Malik Lund
 *
 */
public class Product {
	private int id;
	private String name;
	private String description;
	private Treatment treatment;
	
	
	public Product(int id, String name, String description, Treatment treatment) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.treatment = treatment;
	}

	/**
	 * Get the treatment associated with this product
	 * type.
	 * @return
	 */
	public Treatment getTreatment() {
		return treatment;
	}

	/**
	 * Replace the treatment associated with this product
	 * type.
	 * @param treatment
	 */
	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	/**
	 * 
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @param newName
	 */
	public void setName(String newName){
		this.name = newName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 
	 * @param newDescription
	 */
	public void setDescription(String newDescription){
		this.description = newDescription;
	}
	
	
}
