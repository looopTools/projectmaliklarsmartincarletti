/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubTreatment
 */
package carletti.model;

public class SubTreatment {
	
	/*
	 * Holds the drying time for minimum, prime and maximum drying time
	 */
	private long dryMin, dryPrime, dryMax;
	private String name;
	
	public SubTreatment(String name, long dryMin, long dryPrime, long dryMax){
		this.name = name;
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
	
}
