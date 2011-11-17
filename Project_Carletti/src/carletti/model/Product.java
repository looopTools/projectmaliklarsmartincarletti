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
	
	
	public Product(){
		description = new String();
	}
	
	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String newDescription){
		this.description = newDescription;
	}
	
	
}
