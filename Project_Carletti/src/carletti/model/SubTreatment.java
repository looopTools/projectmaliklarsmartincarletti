/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubTreatment
 */
package carletti.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SubTreatment {
	
	/*
	 * Holds the drying time for minimum, prime and maximum drying time
	 */
	@Id
	private long dryMin, dryPrime, dryMax;
	private String name;
	
	public SubTreatment(String name, long dryMin, long dryPrime, long dryMax){
		this.setName(name);
		this.dryMin = dryMin;
		this.dryPrime = dryPrime;
		this.dryMax = dryMax;
		
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
