package carletti.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;



@Entity
/**
 * Treatment represents the various drying treatments a product 
 * needs to complete before it is a finished product.
 * 
 * @author Malik Lund
 *
 */
public class Treatment {
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<SubTreatment> subTreatments;
	private int number;
	
	/**
	 * 
	 * @param name
	 */
	
	public Treatment()
	{
		
	}
	
	public Treatment(int id, String name){
		this.name = name;
		this.id = id;
		subTreatments = new ArrayList<SubTreatment>();
		number = 0;
	}
	
	/**
	 * Creates and appends a new treatment to the list.
	 * @param name The name of the sub-treatment.
	 * @param dryMin Minimum drying time.
	 * @param dryPrime Optimal or prime drying time.
	 * @param dryMax Maximal drying time.
	 */
	public void createSubTreatment(String name, long dryMin, long dryPrime, long dryMax){
		SubTreatment st = new SubTreatment(name, number, dryMin, dryPrime, dryMax);
		subTreatments.add(st);
		number = number + 1;
	}
	
	public String getName(){
		return name;
	}

	public int getId() {
		return id;
	}

	/**
	 * OBS: Altering the returned list has no effect on the
	 *      internal list (though altering each object in the
	 *      list have an effect).
	 * @return A new List.
	 */
	public List<SubTreatment> getSubTreatments() {
		return new ArrayList<SubTreatment>(subTreatments);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString()
	{
		return name + "\n" + getSubTreatments().toString() + "\n";
	}
}
