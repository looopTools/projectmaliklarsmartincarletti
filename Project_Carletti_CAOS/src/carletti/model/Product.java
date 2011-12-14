package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * Product encapsulates the name,
 * description and treatment of each sub-product.
 * 
 * @author Malik Lund
 *
 */
@Entity
public class Product
{
	private int id;
	private String name;
	private String description;
	private Treatment treatment;
	
	/**
	 * Create a Product-object. Id is generated automatically.
	 * @param name A short name for the product.
	 * @param description A description of the product.
	 * @param treatment A treatment plan for the product.
	 */
	public Product(int id, String name, String description, Treatment treatment) throws NullPointerException{
		this.id = id;
		this.name = name;
		this.description = description;
		this.treatment = treatment;
	}
	
	/**
	 * Empty constructor for JPA.
	 */
	public Product()
	{
		
	}

	/**
	 * Get the treatment associated with this product
	 * type.
	 * @return The Treatment object associated with this product type.
	 */
	public Treatment getTreatment() {
		return treatment;
	}

	/**
	 * Replace the treatment associated with this product
	 * type.
	 * @param treatment The new Treatment object to be associated with this product type.
	 */
	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	/**
	 * Get the id given by the database.
	 * @return This objects id represented by an int value.
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Get the name of this product.
	 * @return A String object containing the name.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Replace the name of this product.
	 * @param newName The new name as a String object.
	 */
	public void setName(String newName){
		this.name = newName;
	}
	
	/**
	 * Get a description of the product.
	 * @return The description of the product as a String.
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Replace the description of this product.
	 * @param newDescription A String object that is to be the new description.
	 */
	public void setDescription(String newDescription)throws NullPointerException{
		this.description = newDescription;
	}
	
//	Added by Martin
	public String toString()
	{
		return name;
	}
//
}
