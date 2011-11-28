/**
 *@author Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 *@class Position
 *@programmer Lars Nielsen
 */

package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Position {

	@Id 
	private String posID;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private SubProduct sub; // Holds which product is on the position

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
	public boolean putSubProductOnPosition(SubProduct sub) {
		// boolean taskCompleted = false;
		if (sub == null) {
			this.sub = sub;
			return true;
		} else {
			return false;
		}
	}

	/*
	 * @precon There can't be removed a SubProduct from a position, where there
	 * are non located
	 */
	public boolean removeSubProductFromPosition() {
		if (sub == null) {
			return false;
		} else {
			sub = null;
			return true;
		}
	}
}
