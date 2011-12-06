package carletti.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubTreatment
 */
public class SubTreatment {
	
	/*
	 * Holds the drying time for minimum, prime and maximum drying time
	 */
	@Id
	@GeneratedValue
	private int id; //Is need for the JPA identification 
	private long dryMin, dryPrime, dryMax;
	private String name;
	
	SubTreatment(String name, long dryMin, long dryPrime, long dryMax){
		this.setName(name);
		this.dryMin = dryMin;
		this.dryPrime = dryPrime;
		this.dryMax = dryMax;
		
	}
	
	public SubTreatment()
	{
		
	}

	public long getDryMin() {
		return dryMin;
	}

	public void setDryMin(long dryMin) {
		this.dryMin = dryMin;
	}

	public long getDryPrime() {
		return dryPrime;
	}

	public void setDryPrime(long dryPrime) {
		this.dryPrime = dryPrime;
	}

	public long getDryMax() {
		return dryMax;
	}

	public void setDryMax(long dryMax) {
		this.dryMax = dryMax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString()
	{
		return "\n" + name + "\n" + "min= " + getDryMin() + "\nprime= " + getDryPrime() + "\nmax= " + getDryMax();
	}
}
