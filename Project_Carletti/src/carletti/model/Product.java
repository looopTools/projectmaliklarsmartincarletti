package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * Product represents encapsulates the name,
 * description and treatment of each sub-product.
 * 
 * @author Malik Lund
 *
 */
@Entity
public class Product {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	@OneToOne(cascade={CascadeType.PERSIST})
	private Treatment treatment;
	
	/**
	 * Create a Product-object. Id is generated automatically.
	 * @param name A short name for the product.
	 * @param description A description of the product.
	 * @param treatment A treatment plan for the product.
	 */
	public Product(String name, String description, Treatment treatment) {
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
	 * Get the id given by the database.
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Get the name of this product.
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Replace the name of this product.
	 * @param newName The new name.
	 */
	public void setName(String newName){
		this.name = newName;
	}
	
	/**
	 * Get a description of the product.
	 * @return
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Replace the description of this product.
	 * @param newDescription
	 */
	public void setDescription(String newDescription){
		this.description = newDescription;
	}
	
//	Added by Martin
	public String toString()
	{
		return name;
	}
//
}
