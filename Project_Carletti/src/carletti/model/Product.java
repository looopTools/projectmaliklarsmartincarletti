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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String description;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Treatment treatment;
	
	
	public Product(String name, String description, Treatment treatment) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.treatment = treatment;
	}
	
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
	
//	Added by Martin
	public String toString()
	{
		return name;
	}
//
}
