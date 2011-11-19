/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubProduct
 */
package carletti.model;

import java.util.ArrayList;

public class SubProduct {
	
	private int id; //To have a key for the database
	private String name;
	private State state; //The current state which a SubProduct is in 
	private ArrayList<SubTreatment> subtreatments = new ArrayList<SubTreatment>();
	private int currentSubTreatmentIndex = -1;
	private Product product;
	
	public SubProduct(int id, String name, State state, Product product) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.product = product;
		subtreatments.addAll(product.getTreatment().getSubTreatments());
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

	public ArrayList<SubTreatment> getSubtreatments() {
		return new ArrayList<SubTreatment>(subtreatments);
	}

	public void setSubtreatments(ArrayList<SubTreatment> subtreatments) {
		this.subtreatments = subtreatments;
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

	/*
	 * @post subtreatments is in sort by need
	 */
	public void nextSubTreatment(){
		setCurrentSubTreatmentIndex(getCurrentSubTreatmentIndex()+1);
	}
	
	public SubTreatment getCurrentSubTreatment(){
		return subtreatments.get(getCurrentSubTreatmentIndex());
	}
	
//	Added by Martin
	public String toString()
	{
		return getId() + "" + name + "\n" + getState() + "\n" + getCurrentSubTreatmentIndex() + "" + " / " + getSubtreatments().size() + "";
	}
//
}
