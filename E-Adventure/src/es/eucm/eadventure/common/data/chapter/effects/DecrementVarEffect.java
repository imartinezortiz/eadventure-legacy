package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that decrements a var according to a given value
 */
public class DecrementVarEffect implements Effect {

	/**
	 * Name of the var
	 */
	private String idVar;
	
	/**
	 * Value to be decremented
	 */
	private int value;

	/**
	 * Creates a new Activate effect.
	 * 
	 * @param idVar
	 *            the id of the var to be activated
	 */
	public DecrementVarEffect( String idVar, int value ) {
		this.idVar = idVar;
		this.value = value;
	}

	public int getType( ) {
		return DECREMENT_VAR;
	}

	/**
	 * Returns the idVar
	 * 
	 * @return String containing the idVar
	 */
	public String getIdVar( ) {
		return idVar;
	}

	/**
	 * Sets the new idVar
	 * 
	 * @param idVar
	 *            New idVar
	 */
	public void setIdVar( String idVar ) {
		this.idVar = idVar;
	}

	/**
	 * @return the value
	 */
	public int getDecrement() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setDecrement(int value) {
		this.value = value;
	}
}
