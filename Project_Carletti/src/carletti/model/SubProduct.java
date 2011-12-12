package carletti.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Column;


@Entity
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubProduct
 */
public class SubProduct implements Comparable<SubProduct> {

	@Id
	@GeneratedValue
	private int id; // To have a key for the database
	@Column(nullable = false)
	private String name;
	@Enumerated(EnumType.STRING)
	private State state; // The current state which a SubProduct is in

	@OneToMany
	private List<SubTreatment> subtreatments = new ArrayList<SubTreatment>();
	private int currentSubTreatmentIndex = 0;
	@JoinColumn(nullable = false)
	@OneToOne(cascade = { CascadeType.PERSIST })
	private Product product;
	private long timeAdded;
	@OneToOne(cascade={CascadeType.PERSIST})
	private Position position;

	public SubProduct(String name, Product product, long timeAdded,
			Position position) {

		this.name = name;
		this.state = State.DRYING;
		this.setProduct(product);
		this.timeAdded = timeAdded;
		this.setPosition(position);
	}

	public SubProduct() {

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

	private void setSubtreatments(List<SubTreatment> subtreatments) {
		this.subtreatments = subtreatments;
		setCurrentSubTreatmentIndex(0);
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

	private void setProduct(Product product) {
		this.product = product;
		setSubtreatments(product.getTreatment().getSubTreatments());
	}

	/*
	 * @post subtreatments is in sort by need
	 */
	public void nextSubTreatment() {
		if (currentSubTreatmentIndex < (subtreatments.size() - 1)) {
			setCurrentSubTreatmentIndex(getCurrentSubTreatmentIndex() + 1);
			setState(State.DRYING); // Martin
		} else if (currentSubTreatmentIndex >= (subtreatments.size() - 1)) {
			setState(State.DONE);
		}
	}

	public SubTreatment getCurrentSubTreatment() {
		return subtreatments.get(getCurrentSubTreatmentIndex());
	}

	public String getTime(long time) {
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy '-' HH:mm:ss");
		return sd.format(time);

	}

	// -------Lars--------

	public Position getPosition() {
		return position;
	}

	void setPositionUD(Position position) {
		this.position = position;
	}

	public void setPosition(Position position) {
		if (position == null){
			this.position.putSubProductOnPositionUD(null);
		} else {
			position.putSubProductOnPositionUD(this);
		}
		setPositionUD(position);
	}

	/**
	 * 
	 * @author Martin
	 */
	@Override
	public String toString() {
		return LongToStringParser.parseLongToString(timeLeft()) + " | " + getTime(timeAdded) + " | "
				+ getId() + " " + name + " " + getState() + " " + (getCurrentSubTreatmentIndex() + 1)
				+ "" + " / " + getSubtreatments().size();
	}

	/**
	 * 
	 * @author Martin
	 * @return
	 */
	public long timeLeft() {
		long max = (getCurrentSubTreatment().getDryMax() + timeAdded - System
				.currentTimeMillis());

		return max;
	}

	/** 
	 * 
	 * @author Martin
	 */
	
//	@Override
//	public int compareTo(SubProduct otherSubProduct) {
//		return (int) (timeLeft() - otherSubProduct.timeLeft());
//	}
	
	@Override
	public int compareTo(SubProduct otherSupProduct)
	{
	 	long l = (timeLeft() - otherSupProduct.timeLeft());
	
	 	int result = 0;
	 	
	 	if (l < 0)
	 		result = -1; 
	 	
	 	else if (l > 0)
	 		result = 1;
	 
		return result;
		
	}
}
