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
	
	public SubProduct(int id, String name, State state) {
		this.id = id;
		this.name = name;
		this.state = state;
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
	
	

}
