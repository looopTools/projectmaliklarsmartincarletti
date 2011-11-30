/**
 *@author Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 *@class Position
 *@programmer Lars Nielsen
 */

package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Position {

	@Id
	@GeneratedValue
	private int id;
	private String posID;
	@OneToOne(mappedBy = "position")
	private SubProduct sp; // Holds which product is on the position
	public Position(String posID) {
		this.posID = posID;
	}
	
	public Position()
	{
		
	}

	public String getPosID() {
		return posID;
	}

	public void setPosID(String posID) {
		this.posID = posID;
	}

	/*
	 * @precon There can't be put a SubProduct on a position where there already
	 * is another SubProduct already position
	 */
	public boolean putSubProductOnPosition(SubProduct sp) {
		 boolean taskCompleted = false;
		if (sp == null) {
			putSubProductOnPositionUD(sp);
			sp.setPositionUD(this);
			return true;
		} else {
			return false;
		}
//		return false;
	}

	/*
	 * @precon There can't be removed a SubProduct from a position, where there
	 * are non located
	 */
	public boolean removeSubProductFromPosition() {
		if (sp == null) {
			return false;
		} else {
			sp = null;
			return true;
		}
//		return false;
	}
	
	void putSubProductOnPositionUD(SubProduct sp){
		this.sp = sp;
	}
	
	
}
