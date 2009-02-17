package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that deactivates a flag.
 */
public class DeactivateEffect implements Effect {

	/**
	 * Name of the flag to be activated
	 */
	private String idFlag;

	/**
	 * Creates a new DeactivateEffect.
	 * 
	 * @param idFlag
	 *            the id of the flag to be deactivated
	 */
	public DeactivateEffect( String idFlag ) {
		this.idFlag = idFlag;
	}

	public int getType( ) {
		return DEACTIVATE;
	}

	/**
	 * Returns the idFlag
	 * 
	 * @return String containing the idFlag
	 */
	public String getIdFlag( ) {
		return idFlag;
	}

	/**
	 * Sets the new idFlag
	 * 
	 * @param idFlag
	 *            New idFlag
	 */
	public void setIdFlag( String idFlag ) {
		this.idFlag = idFlag;
	}
	
	public Object clone() throws CloneNotSupportedException {
		DeactivateEffect de = (DeactivateEffect) super.clone();
		de.idFlag = (idFlag != null ? new String(idFlag) : null);
		return de;
	}
}
