package es.eucm.eadventure.common.data.chapter.conversation.line;

import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.data.chapter.elements.Player;

/**
 * This class stores a single conversation line, along with the name of the speaker character.
 */
public class ConversationLine implements Cloneable, Named {

	/**
	 * Constant for the player identifier for the lines.
	 */
	public static final String PLAYER = Player.IDENTIFIER;

	/**
	 * String that holds the name of the character.
	 */
	private String name;

	/**
	 * Sentence said by the character.
	 */
	private String text;
	
	/**
	 * Path for the audio track where the line is recorded. Its use is optional.
	 */
	private String audioPath;
	
	/**
	 * Tell if the line has to be read by synthesizer
	 */
	private boolean synthesizerVoice;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Name of the character
	 * @param text
	 *            Sentence
	 */
	public ConversationLine( String name, String text ) {
		this.name = name;
		this.text = text;
		this.synthesizerVoice = false;
	}

	/**
	 * Returns the name of the character.
	 * 
	 * @return The name of the character
	 */
	public String getName( ) {
		return name;
	}

	/**
	 * Returns the text of the converstational line.
	 * 
	 * @return The text of the conversational line
	 */
	public String getText( ) {
		return text;
	}

	/**
	 * Returns if the line belongs to the player.
	 * 
	 * @return True if the line belongs to the player, false otherwise
	 */
	public boolean isPlayerLine( ) {
		return name.equals( PLAYER );
	}

	/**
	 * Sets the new name of the line.
	 * 
	 * @param name
	 *            New name
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * Sets the new text of the line.
	 * 
	 * @param text
	 *            New text
	 */
	public void setText( String text ) {
		this.text = text;
	}

	/**
	 * @return the audioPath
	 */
	public String getAudioPath( ) {
		return audioPath;
	}

	/**
	 * @param audioPath the audioPath to set
	 */
	public void setAudioPath( String audioPath ) {
		this.audioPath = audioPath;
	}
	
	/**
	 * Returns true if the audio path is valid. That is when it is not null and different to ""
	 */
	public boolean isValidAudio(){
		return audioPath!=null && !audioPath.equals( "" );
	}

	/**
	 * Returns if the line has to be read by synthesizer
	 * 
	 * @return
	 * 		if this line has to be read by synthesizer
	 */
	public boolean getSynthesizerVoice() {
		return synthesizerVoice;
	}

	/**
	 * Set if the line to be read by synthesizer
	 * 
	 * @param synthesizerVoice
	 * 				true for to be read by synthesizer
	 */
	public void setSynthesizerVoice(boolean synthesizerVoice) {
		this.synthesizerVoice = synthesizerVoice;
	}

	public Object clone() throws CloneNotSupportedException {
		ConversationLine cl = (ConversationLine) super.clone();
		cl.audioPath = (audioPath != null ? new String(audioPath) : null);
		cl.name = (name != null ? new String(name) : null);
		cl.synthesizerVoice = synthesizerVoice;
		cl.text = (text != null ? new String(text) : null);
		return cl;
	}	

	
}