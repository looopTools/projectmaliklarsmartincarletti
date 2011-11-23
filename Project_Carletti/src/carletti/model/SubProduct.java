/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubProduct
 */
package carletti.model;

import java.util.ArrayList;
import java.util.List;

public class SubProduct {
	
	private int id; //To have a key for the database
	private String name;
	private State state; //The current state which a SubProduct is in 
	private List<SubTreatment> subtreatments = new ArrayList<SubTreatment>();
	private int currentSubTreatmentIndex = 0;
	private Product product;
	private long timeAdded;
	
	public SubProduct(int id, String name, Product product, long timeAdded) {
		this.id = id;
		this.name = name;
		this.state = State.DRYING;
		this.setProduct(product);
		this.timeAdded = timeAdded;
	}

	public long getTimeAdded() {
		return timeAdded;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public List<SubTreatment> getSubtreatments() {
		return new ArrayList<SubTreatment>(subtreatments);
	}

	public void setSubtreatments(List<SubTreatment> subtreatments) {
		this.subtreatments = subtreatments;
		setCurrentSubTreatmentIndex(0);
	}
	
	public void addSubTreatment(SubTreatment st){
		subtreatments.add(st);
	}
	
	public void removeSubTreatment(SubTreatment st){
		subtreatments.remove(st);
	}
	
	public int getCurrentSubTreatmentIndex() {
		return currentSubTreatmentIndex;
	}

	public void setCurrentSubTreatmentIndex(int currentSubTreatmentIndex) {
		this.currentSubTreatmentIndex = currentSubTreatmentIndex;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		setSubtreatments(product.getTreatment().getSubTreatments());
	}

	/*
	 * @post subtreatments is in sort by need
	 */
	public void nextSubTreatment(){
		if (currentSubTreatmentIndex < (subtreatments.size() - 1)){
			setCurrentSubTreatmentIndex(getCurrentSubTreatmentIndex()+1);
		}
	}
	
	public SubTreatment getCurrentSubTreatment(){
		return subtreatments.get(getCurrentSubTreatmentIndex());
	}
	
	public int stringThis(){
		return getCurrentSubTreatmentIndex() +1;
	}
//	Added by Martin
	public String toString()
	{
		return getId() + " " + name + " " + getState() + " " + stringThis() + "" + " / " + getSubtreatments().size();
	}
//
}
