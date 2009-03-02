package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.util.ArrayList;

import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * A list of effects that can be triggered by an unique
 * player's action during the game.
 */
public class FunctionalEffects {

    /**
     * Stores if the effect cancel the normal course of the action
     */
    private boolean hasCancelAction;

    /**
     * List of effects to be triggered
     */
    private ArrayList<FunctionalEffect> functionalEffects;

    /**
     * Creates a new, empty list of FunctionalEffects.
     */
    public FunctionalEffects(  ) {
        functionalEffects = new ArrayList<FunctionalEffect>( );
        hasCancelAction = false;
    }

    /**
     * Creates a new, empty list of FunctionalEffects.
     */
    public FunctionalEffects( Effects effects ) {
    	this();
    	// Add a new functional effect to the list for each effect in effects structure
    	for ( Effect effect: effects.getEffects() ){
    		FunctionalEffect fe = FunctionalEffect.buildFunctionalEffect(effect);
    		if (fe!=null)
    			functionalEffects.add(fe);
    	}
    	// If the effects structure has cancel action, add it
    	hasCancelAction = effects.hasCancelAction( );
    }
    
    /**
     * Adds a new effect to the list.
     * @param effect the effect to be added
     */
    private void add( FunctionalEffect effect ) {
        functionalEffects.add( effect );
    }

    /**
     * Return the effect in the given position.
     * @param index the effect position
     * @return the effect in the given position
     */
    public FunctionalEffect getEffect( int index ) {
        return functionalEffects.get( index );
    }

    /**
     * Sets whether the list of effects has a cancel action.
     * @param hasCancelAction true if the list of effects has a cancel action, false otherwise
     */
    public void setHasCancelAction( boolean hasCancelAction ) {
        this.hasCancelAction = hasCancelAction;
    }

    /**
     * Returns whether the list of effects has a cancel action.
     * @return true if the list of effects has a cancel action, false otherwise
     */
    public boolean hasCancelAction( ) {
        return hasCancelAction;
    }

    /**
     * Queues the effects in the game effects queue to be done when possible.
     */
    public static void storeAllEffects( Effects effects ) {
        Game.getInstance( ).storeEffectsInQueue( new FunctionalEffects(effects).getEffects() );
    }
    
    public static void storeAllEffects( Effects effects, boolean fromConversation ) {
        Game.getInstance( ).storeEffectsInQueue( new FunctionalEffects(effects).getEffects(), fromConversation );
    }

	/**
	 * @return the functionalEffects
	 */
	public ArrayList<FunctionalEffect> getEffects() {
		return functionalEffects;
	}

}